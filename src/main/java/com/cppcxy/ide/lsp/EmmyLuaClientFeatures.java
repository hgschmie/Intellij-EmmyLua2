package com.cppcxy.ide.lsp;

import com.redhat.devtools.lsp4ij.client.features.LSPClientFeatures;
import org.eclipse.lsp4j.InitializeParams;
import org.eclipse.lsp4j.WorkspaceFolder;
import org.jetbrains.annotations.NotNull;

import java.net.URI;
import java.nio.file.Path;
import java.util.List;

/** Keeps the default analyzer out of source roots handled by integration-owned servers. */
public final class EmmyLuaClientFeatures extends LSPClientFeatures {
    // One immutable snapshot per initialization, also read by workspace/configuration.
    private volatile List<Path> ownedRoots = List.of();

    @Override public void initializeParams(@NotNull InitializeParams params) {
        ownedRoots = EmmyLuaServerRouting.getOwnedRoots(getProject());
        excludeOwnedRoots(params, ownedRoots);
    }

    List<Path> ownedRoots() {
        return ownedRoots;
    }

    static void excludeOwnedRoots(InitializeParams params, List<Path> owned) {
        if (owned.isEmpty()) return;
        List<WorkspaceFolder> folders = params.getWorkspaceFolders();
        if (folders != null) {
            folders = folders.stream().filter(folder -> !isOwned(folder.getUri(), owned)).toList();
            params.setWorkspaceFolders(folders);
        }
        // An empty workspaceFolders list otherwise falls back to rootUri in the analyzer.
        if (isOwned(params.getRootUri(), owned)) {
            String uri = folders == null || folders.isEmpty() ? null : folders.get(0).getUri();
            params.setRootUri(uri);
            params.setRootPath(uri == null ? null : Path.of(URI.create(uri)).toString());
        }
        params.getCapabilities().getWorkspace().setConfiguration(true);
    }

    private static boolean isOwned(String uri, List<Path> owned) {
        if (uri == null) return false;
        try {
            Path path = canonical(Path.of(URI.create(uri)));
            return owned.stream().anyMatch(root -> path.startsWith(canonical(root)));
        } catch (IllegalArgumentException e) {
            return false; // A non-file URI cannot belong to a local directory.
        }
    }

    private static Path canonical(Path path) {
        try { return path.toRealPath(); }
        catch (java.io.IOException e) { return path.toAbsolutePath().normalize(); }
    }
}
