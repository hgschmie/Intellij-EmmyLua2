package com.cppcxy.ide.lsp;

import com.google.gson.JsonParser;
import org.eclipse.lsp4j.*;
import org.junit.Test;

import java.nio.file.Path;
import java.util.List;

import static org.junit.Assert.*;

public class EmmyLuaWorkspaceOwnershipTest {
    private final Path root = Path.of(System.getProperty("java.io.tmpdir"), "ownership-project").toAbsolutePath();
    private final Path mod = root.resolve("mod");

    private InitializeParams params(Path... roots) {
        var params = new InitializeParams();
        params.setRootUri(roots[0].toUri().toString());
        params.setRootPath(roots[0].toString());
        params.setWorkspaceFolders(java.util.Arrays.stream(roots)
                .map(path -> new WorkspaceFolder(path.toUri().toString(), path.getFileName().toString())).toList());
        var capabilities = new ClientCapabilities();
        capabilities.setWorkspace(new WorkspaceClientCapabilities());
        params.setCapabilities(capabilities);
        return params;
    }

    @Test public void excludesOwnedRootsButKeepsParentAndSimilarlyNamedSibling() {
        var params = params(root, mod, mod.resolve("scripts"), root.resolve("mod-extra"));
        EmmyLuaClientFeatures.excludeOwnedRoots(params, List.of(mod));
        assertEquals(List.of(root.toUri().toString(), root.resolve("mod-extra").toUri().toString()),
                params.getWorkspaceFolders().stream().map(WorkspaceFolder::getUri).toList());
        assertEquals(root.toUri().toString(), params.getRootUri());
    }

    @Test public void removesRootUriFallbackWhenEverythingIsOwned() {
        var params = params(mod);
        EmmyLuaClientFeatures.excludeOwnedRoots(params, List.of(mod));
        assertTrue(params.getWorkspaceFolders().isEmpty());
        assertNull(params.getRootUri());
        assertNull(params.getRootPath());
    }

    @Test public void usesUnclaimedRootWhenOriginalRootIsOwned() {
        var plain = root.resolve("plain");
        var params = params(mod, plain);
        EmmyLuaClientFeatures.excludeOwnedRoots(params, List.of(mod));
        assertEquals(plain.toUri().toString(), params.getRootUri());
        assertEquals(plain.toString(), params.getRootPath());
    }

    @Test public void leavesInitializationUntouchedWithoutOwnership() {
        var params = params(mod);
        EmmyLuaClientFeatures.excludeOwnedRoots(params, List.of());
        assertEquals(mod.toUri().toString(), params.getRootUri());
        assertEquals(1, params.getWorkspaceFolders().size());
        assertNull(params.getCapabilities().getWorkspace().getConfiguration());
    }

    @Test public void addsExclusionsWithoutMutatingSettingsOrLibraries() {
        var settings = JsonParser.parseString("""
                {"diagnostics":{"disable":["unused"]},
                 "workspace":{"ignoreDir":["vendor"], "library":["shared"], "packages":["dep"]}}
                """).getAsJsonObject();
        var before = settings.deepCopy();
        var result = EmmyLuaLanguageClient.withOwnedRoots(settings, List.of(mod));
        assertEquals(before, settings);
        assertEquals(before.get("diagnostics"), result.get("diagnostics"));
        var ws = result.getAsJsonObject("workspace");
        assertEquals(before.getAsJsonObject("workspace").get("library"), ws.get("library"));
        assertEquals(before.getAsJsonObject("workspace").get("packages"), ws.get("packages"));
        assertEquals(2, ws.getAsJsonArray("ignoreDir").size());
        assertEquals(mod.toString(), ws.getAsJsonArray("ignoreDir").get(1).getAsString());
        assertEquals(1, EmmyLuaLanguageClient.withOwnedRoots(null, List.of(mod))
                .getAsJsonObject("workspace").getAsJsonArray("ignoreDir").size());
    }
}
