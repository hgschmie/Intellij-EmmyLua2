import org.jetbrains.intellij.platform.gradle.TestFrameworkType
import de.undercouch.gradle.tasks.download.Download

plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "2.3.0"
    id("org.jetbrains.intellij.platform") version "2.6.0"
    id("de.undercouch.download") version "5.6.0"
}

// ============= 项目配置 =============
group = "com.cppcxy"

// 版本配置
object Versions {
    // Downloaded fallback binaries; only macOS arm64 is rebuilt locally.
    const val emmyluaAnalyzer = "0.25.1"
    const val patchedAnalyzer = "0.30.0"
    const val emmyDebugger = "1.9.2"
    const val jvm = "17"
    const val ideaSDK = "2026.2"
}

// 构建数据配置
data class BuildData(
    val ideaSDKShortVersion: String,
    val sinceBuild: String,
    val untilBuild: String,
)

private val buildDataList = listOf(
    BuildData(
        ideaSDKShortVersion = "262",
        sinceBuild = "261",
        untilBuild = "262.*"
    )
)

// 动态版本配置
private val buildVersion = System.getProperty("IDEA_VER") ?: buildDataList.first().ideaSDKShortVersion
private val buildVersionData = buildDataList.find { it.ideaSDKShortVersion == buildVersion }
    ?: error("Unsupported IDEA version: $buildVersion")
private val runnerNumber = System.getenv("RUNNER_NUMBER") ?: "141"

val localAnalyzerPath = providers.gradleProperty("localAnalyzerPath").orNull
val patchedSuffix = if (localAnalyzerPath == null) "patched" else "patched-modules"
val analyzerVersion = if (localAnalyzerPath == null) Versions.emmyluaAnalyzer else Versions.patchedAnalyzer
version = "${analyzerVersion}-${runnerNumber}-IDEA${buildVersion}-${patchedSuffix}"

// 下载URL配置
object DownloadUrls {
    private const val emmyluaAnalyzerProjectUrl = "https://github.com/EmmyLuaLs/emmylua-analyzer-rust"
    private const val emmyDebuggerProjectUrl = "https://github.com/EmmyLua/EmmyLuaDebugger"

    // GitHub 镜像配置
    private val githubMirror = System.getProperty("github.mirror") ?: ""
    private fun applyMirror(url: String): String {
        return if (githubMirror.isNotEmpty() && url.startsWith("https://github.com/")) {
            url.replace("https://github.com/", githubMirror)
        } else url
    }

    val emmyluaAnalyzer = arrayOf(
        "$emmyluaAnalyzerProjectUrl/releases/download/${Versions.emmyluaAnalyzer}/emmylua_ls-win32-x64.zip",
        "$emmyluaAnalyzerProjectUrl/releases/download/${Versions.emmyluaAnalyzer}/emmylua_ls-linux-x64.tar.gz",
        "$emmyluaAnalyzerProjectUrl/releases/download/${Versions.emmyluaAnalyzer}/emmylua_ls-darwin-arm64.tar.gz",
        "$emmyluaAnalyzerProjectUrl/releases/download/${Versions.emmyluaAnalyzer}/emmylua_ls-darwin-x64.tar.gz"
    ).map { applyMirror(it) }.toTypedArray()

    val emmyDebugger = arrayOf(
        "$emmyDebuggerProjectUrl/releases/download/${Versions.emmyDebugger}/darwin-arm64.zip",
        "$emmyDebuggerProjectUrl/releases/download/${Versions.emmyDebugger}/darwin-x64.zip",
        "$emmyDebuggerProjectUrl/releases/download/${Versions.emmyDebugger}/linux-x64.zip",
        "$emmyDebuggerProjectUrl/releases/download/${Versions.emmyDebugger}/win32-x64.zip",
        "$emmyDebuggerProjectUrl/releases/download/${Versions.emmyDebugger}/win32-x86.zip"
    ).map { applyMirror(it) }.toTypedArray()
}

// ============= 任务配置 =============

val analyzerDownloadDir = "temp/analyzer/${Versions.emmyluaAnalyzer}"

// 下载Emmy Lua分析器
val downloadEmmyLuaAnalyzer by tasks.registering(Download::class) {
    group = "build setup"
    description = "Download Emmy Lua Analyzer for all platforms"

    src(DownloadUrls.emmyluaAnalyzer)
    dest(analyzerDownloadDir)
    overwrite(false)
}

// 下载Emmy调试器
val downloadEmmyDebugger by tasks.registering(Download::class) {
    group = "build setup"
    description = "Download Emmy Debugger for all platforms"

    src(DownloadUrls.emmyDebugger)
    dest("temp/debugger")
    overwrite(false)
}

// 解压所有下载的文件
val extractDependencies by tasks.registering(Copy::class) {
    group = "build setup"
    description = "Extract downloaded Emmy dependencies"

    dependsOn(downloadEmmyLuaAnalyzer, downloadEmmyDebugger)
    duplicatesStrategy = DuplicatesStrategy.WARN

    // 解压语言服务器
    from(zipTree("$analyzerDownloadDir/emmylua_ls-win32-x64.zip")) {
        into("server/win32-x64")
    }
    from(tarTree("$analyzerDownloadDir/emmylua_ls-linux-x64.tar.gz")) {
        into("server/linux-x64")
    }
    from(tarTree("$analyzerDownloadDir/emmylua_ls-darwin-arm64.tar.gz")) {
        into("server/darwin-arm64")
    }
    from(tarTree("$analyzerDownloadDir/emmylua_ls-darwin-x64.tar.gz")) {
        into("server/darwin-x64")
    }

    // 解压调试器
    from(zipTree("temp/debugger/win32-x86.zip")) {
        into("debugger/windows/x86")
    }
    from(zipTree("temp/debugger/win32-x64.zip")) {
        into("debugger/windows/x64")
    }
    from(zipTree("temp/debugger/darwin-x64.zip")) {
        into("debugger/mac/x64")
    }
    from(zipTree("temp/debugger/darwin-arm64.zip")) {
        into("debugger/mac/arm64")
    }
    from(zipTree("temp/debugger/linux-x64.zip")) {
        into("debugger/linux")
    }

    destinationDir = file("temp/extracted")
}

// 安装依赖到资源目录
val installDependencies by tasks.registering(Copy::class) {
    group = "build setup"
    description = "Install Emmy dependencies to resources directory"

    dependsOn(extractDependencies)
    duplicatesStrategy = DuplicatesStrategy.WARN

    // 复制语言服务器
    from("temp/extracted/server") {
        if (localAnalyzerPath != null) exclude("darwin-arm64/emmylua_ls")
        into("server")
    }
    if (localAnalyzerPath != null) {
        require(file(localAnalyzerPath).isFile) { "Build the local analyzer before packaging" }
        from(localAnalyzerPath) { into("server/darwin-arm64"); rename { "emmylua_ls" } }
        from("analyzer.lock") { into("server/darwin-arm64") }
    }

    // 复制调试器核心文件
    listOf(
        Triple("temp/extracted/debugger/windows/x64", "emmy_core.dll",  "debugger/emmy/windows/x64"),
        Triple("temp/extracted/debugger/windows/x86", "emmy_core.dll",  "debugger/emmy/windows/x86"),
        Triple("temp/extracted/debugger/linux",        "emmy_core.so",   "debugger/emmy/linux"),
        Triple("temp/extracted/debugger/mac/x64",      "emmy_core.dylib","debugger/emmy/mac/x64"),
        Triple("temp/extracted/debugger/mac/arm64",    "emmy_core.dylib","debugger/emmy/mac/arm64"),
        // Attach / Launch debugger tools (Windows only)
        Triple("temp/extracted/debugger/windows/x64", "emmy_tool.exe",  "debugger/emmy/windows/x64"),
        Triple("temp/extracted/debugger/windows/x64", "emmy_hook.dll",  "debugger/emmy/windows/x64"),
        Triple("temp/extracted/debugger/windows/x64", "EasyHook.dll",   "debugger/emmy/windows/x64"),
        Triple("temp/extracted/debugger/windows/x86", "emmy_tool.exe",  "debugger/emmy/windows/x86"),
        Triple("temp/extracted/debugger/windows/x86", "emmy_hook.dll",  "debugger/emmy/windows/x86"),
        Triple("temp/extracted/debugger/windows/x86", "EasyHook.dll",   "debugger/emmy/windows/x86")
    ).forEach { (sourcePath, fileName, targetPath) ->
        from(sourcePath) {
            include(fileName)
            into(targetPath)
        }
    }

    destinationDir = file("src/main/resources")
}

// 清理任务
val cleanDependencies by tasks.registering(Delete::class) {
    group = "build setup"
    description = "Clean downloaded and extracted dependencies"

    delete("temp")
}

// ============= 仓库配置 =============
repositories {
    // 保留原有仓库作为备用
    mavenCentral()
    google()
    gradlePluginPortal()

    intellijPlatform {
        defaultRepositories()
    }
}

// ============= 依赖配置 =============
dependencies {
    testImplementation("junit:junit:4.13.2")
    intellijPlatform {
        testFramework(TestFrameworkType.Platform)
        val localPlatformPath = providers.gradleProperty("localPlatformPath").orNull
        if (localPlatformPath != null) {
            local(localPlatformPath)
        } else {
            intellijIdeaUltimate(Versions.ideaSDK)
        }

        bundledPlugins("com.intellij.java", "org.jetbrains.kotlin")
        val localLsp4ijPath = providers.gradleProperty("localLsp4ijPath").orNull
        if (localLsp4ijPath != null) {
            localPlugin(localLsp4ijPath)
        } else {
            plugins("com.redhat.devtools.lsp4ij:0.19.0")
        }
    }
}

// ============= 源码集配置 =============
sourceSets {
    main {
        java.srcDirs("gen")
        resources.srcDirs("resources")
        // 排除二进制文件，它们将通过 prepareSandbox 任务单独处理
        resources.exclude("debugger/**/*")
        resources.exclude("server/**/*")
    }
}

// ============= IntelliJ 平台配置 =============
intellijPlatform {
    buildSearchableOptions = false
    projectName = "IntelliJ-EmmyLua2"

    pluginConfiguration {
        name = "EmmyLua2"
    }

    publishing {
        token = System.getenv("PUBLISH_TOKEN")
    }

    signing {
        certificateChain = System.getenv("CERTIFICATE_CHAIN")
        privateKey = System.getenv("PRIVATE_KEY")
        password = System.getenv("PRIVATE_KEY_PASSWORD")
    }
}

// ============= 任务配置 =============
tasks {
    withType<Test> {
        systemProperty("idea.load.plugins.id", "com.cppcxy.Intellij-EmmyLua,com.redhat.devtools.lsp4ij")
    }

    // Java 编译配置
    withType<JavaCompile> {
        sourceCompatibility = Versions.jvm
        targetCompatibility = Versions.jvm
        options.encoding = "UTF-8"
    }

    // Kotlin 编译配置
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.fromTarget(Versions.jvm))
        }
    }

    // 插件XML配置
    patchPluginXml {
        dependsOn(installDependencies)
        sinceBuild.set(buildVersionData.sinceBuild)
        untilBuild.set(buildVersionData.untilBuild)
    }

    buildPlugin {
        dependsOn(installDependencies)
        // Archive tasks default to 0644; the server must remain executable after installation.
        filesMatching("**/server/**/emmylua_ls") {
            permissions { unix("0755") }
        }
    }

    // 准备沙盒环境
    prepareSandbox {
        dependsOn(installDependencies)

        // into() 相对于 plugins/ 目录，需要加插件目录名前缀
        val pluginDir = intellijPlatform.projectName.get()

        from("src/main/resources/server") {
            into("$pluginDir/server")
            filesMatching("**/emmylua_ls") { permissions { unix("0755") } }
        }

        from("src/main/resources/debugger") {
            into("$pluginDir/debugger")
        }

    }

    // 清理任务
    clean {
        dependsOn(cleanDependencies)
    }
}
