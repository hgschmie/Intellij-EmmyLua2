package com.cppcxy.ide.lsp;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.intellij.openapi.project.Project;
import com.redhat.devtools.lsp4ij.client.LanguageClientImpl;
import org.eclipse.lsp4j.ConfigurationParams;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public final class EmmyLuaLanguageClient extends LanguageClientImpl {
    public EmmyLuaLanguageClient(Project project) {
        super(project);
    }

    @Override public CompletableFuture<List<Object>> configuration(ConfigurationParams params) {
        var owned = ((EmmyLuaClientFeatures) getClientFeatures()).ownedRoots();
        return super.configuration(params).thenApply(settings -> {
            if (owned.isEmpty()) return settings;
            var result = new ArrayList<>(settings);
            for (int i = 0; i < params.getItems().size(); i++) {
                if ("emmylua".equals(params.getItems().get(i).getSection())) {
                    result.set(i, withOwnedRoots(settings.get(i), owned));
                }
            }
            return result;
        });
    }

    static JsonObject withOwnedRoots(Object settings, List<Path> owned) {
        var result = settings instanceof JsonObject json ? json.deepCopy() : new JsonObject();
        var workspace = result.has("workspace") && result.get("workspace").isJsonObject()
                ? result.getAsJsonObject("workspace") : new JsonObject();
        // Dotted keys take precedence when both notations occur in one layer.
        var existing = result.has("workspace.ignoreDir")
                ? result.remove("workspace.ignoreDir") : workspace.get("ignoreDir");
        var ignored = existing != null && existing.isJsonArray() ? existing.getAsJsonArray() : new JsonArray();
        owned.forEach(path -> ignored.add(path.toString()));
        workspace.add("ignoreDir", ignored);
        result.add("workspace", workspace);
        // The analyzer appends this to native configuration. Explicit libraries and
        // packages can still import an ignored root; user files are never rewritten.
        return result;
    }
}
