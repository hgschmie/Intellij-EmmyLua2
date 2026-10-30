package com.tang.intellij.lua.editor

import com.cppcxy.ide.lsp.GutterInfo
import com.intellij.openapi.project.Project
import org.junit.Assert.*
import org.junit.Test
import java.lang.reflect.Proxy
import java.util.concurrent.CompletableFuture

class LuaGutterCacheManagerTest {
    @Test fun clearingOneProjectFilePreservesOtherProjectsAndUris() {
        val first = project("first")
        val second = project("second")
        val closed = LuaGutterCacheManager.Key(first, "EmmyLua", "file:///shared.lua")
        val otherProject = LuaGutterCacheManager.Key(second, "EmmyLua", closed.uri)
        val otherFile = LuaGutterCacheManager.Key(first, "EmmyLua", "file:///other.lua")
        try {
            for (key in listOf(closed, otherProject, otherFile)) {
                LuaGutterCacheManager.getOrRequest(key, 1, {
                    CompletableFuture.completedFuture(emptyList<GutterInfo>())
                }) {}
            }
            LuaGutterCacheManager.clearCache(first, closed.uri)
            for (key in listOf(otherProject, otherFile)) {
                assertEquals(emptyList<GutterInfo>(), LuaGutterCacheManager.getOrRequest(key, 1, {
                    fail("unrelated cache entry was invalidated")
                    CompletableFuture()
                }) {})
            }
            var requests = 0
            assertNull(LuaGutterCacheManager.getOrRequest(closed, 1, {
                requests++
                CompletableFuture()
            }) {})
            assertEquals(1, requests)
        } finally {
            LuaGutterCacheManager.clearProject(first)
            LuaGutterCacheManager.clearProject(second)
        }
    }

    private fun project(name: String): Project = Proxy.newProxyInstance(
        Project::class.java.classLoader, arrayOf(Project::class.java)
    ) { proxy, method, arguments ->
        when (method.name) {
            "isDisposed" -> false
            "hashCode" -> System.identityHashCode(proxy)
            "equals" -> proxy === arguments?.get(0)
            "toString" -> name
            else -> throw AssertionError("Unexpected project call: ${method.name}")
        }
    } as Project
}
