package com.tang.intellij.lua.editor

import com.cppcxy.ide.lsp.GutterInfo
import java.util.concurrent.CompletableFuture

/** Caches gutter replies for one document revision and rejects invalidated requests. */
internal class GutterRequestCache<K>(private val clock: () -> Long = System::currentTimeMillis) {
    private class Entry(val stamp: Long, var completed: Long = 0, var result: List<GutterInfo>? = null) {
        var future: CompletableFuture<List<GutterInfo>>? = null
        var pending = true
    }

    private val entries = mutableMapOf<K, Entry>()

    fun getOrRequest(
        key: K,
        stamp: Long,
        load: () -> CompletableFuture<List<GutterInfo>>,
        updated: () -> Unit
    ): List<GutterInfo>? {
        val entry: Entry
        val previous: Entry?
        val staleResult: List<GutterInfo>?
        synchronized(entries) {
            val existing = entries[key]
            if (existing != null && existing.stamp == stamp &&
                (existing.pending || clock() - existing.completed <= 2000)) {
                return existing.result
            }
            val sameRevision = existing?.takeIf { it.stamp == stamp }
            entry = Entry(stamp, sameRevision?.completed ?: 0, sameRevision?.result)
            staleResult = entry.result
            previous = entries.put(key, entry)
        }
        previous?.future?.cancel(true)
        val future = try {
            load()
        } catch (error: Throwable) {
            synchronized(entries) { entries.remove(key, entry) }
            throw error
        }
        val current = synchronized(entries) {
            if (entries[key] === entry) {
                entry.future = future
                true
            } else false
        }
        if (!current) future.cancel(true)
        future.whenComplete { infos, error ->
            val accepted = synchronized(entries) {
                if (entries[key] !== entry) false
                else if (error != null || infos == null) {
                    entry.pending = false
                    if (entry.result == null) entries.remove(key)
                    false
                } else {
                    entry.result = infos.toList()
                    entry.completed = clock()
                    entry.pending = false
                    true
                }
            }
            if (accepted) updated()
        }
        return staleResult
    }

    fun invalidate(predicate: (K) -> Boolean) {
        val removed = synchronized(entries) {
            val keys = entries.keys.filter(predicate)
            keys.mapNotNull { entries.remove(it)?.future }
        }
        removed.forEach { it.cancel(true) }
    }
}
