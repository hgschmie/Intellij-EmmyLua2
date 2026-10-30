package com.tang.intellij.lua.editor

import com.cppcxy.ide.lsp.GutterInfo
import com.intellij.openapi.progress.ProcessCanceledException
import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeUnit

/** Resolves one gutter request without treating unavailable servers or missing replies as empty data. */
internal fun <S> requestGutterData(
    findServer: () -> CompletableFuture<out S?>,
    load: (S) -> CompletableFuture<List<GutterInfo>>
): CompletableFuture<List<GutterInfo>> {
    val result = CompletableFuture<List<GutterInfo>>()
    val serverFuture = try {
        findServer()
    } catch (canceled: ProcessCanceledException) {
        throw canceled
    } catch (failure: Exception) {
        result.completeExceptionally(failure)
        return result
    }
    serverFuture.whenComplete { server, error ->
        if (result.isDone) return@whenComplete
        if (error != null) {
            result.completeExceptionally(error)
            return@whenComplete
        }
        if (server == null) {
            result.completeExceptionally(IllegalStateException("Gutter language server is unavailable"))
            return@whenComplete
        }
        try {
            val request = load(server)
            result.whenComplete { _, _ -> if (result.isCompletedExceptionally) request.cancel(true) }
            request.whenComplete { infos, failure ->
                when {
                    failure != null -> result.completeExceptionally(failure)
                    infos == null -> result.completeExceptionally(IllegalStateException("Gutter request returned no response"))
                    else -> result.complete(infos)
                }
            }
        } catch (failure: Exception) {
            result.completeExceptionally(failure)
        }
    }
    return result.orTimeout(10, TimeUnit.SECONDS)
}
