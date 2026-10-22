package com.tang.intellij.lua.editor

import com.cppcxy.ide.lsp.GutterInfo
import java.util.concurrent.CompletableFuture

/** Caches gutter replies for one document revision and rejects invalidated requests. */
internal class GutterRequestCache<K>(private val clock: () -> Long = System::currentTimeMillis) {
    private class Entry(val stamp: Long, var completed: Long = 0) {
        var future: CompletableFuture<List<GutterInfo>>? = null
        var result: List<GutterInfo>? = null
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
        synchronized(entries) {
            val existing = entries[key]
            if (existing != null && existing.stamp == stamp &&
                (existing.result == null || clock() - existing.completed <= 2000)) {
                return existing.result
            }
            entry = Entry(stamp)
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
                    entries.remove(key)
                    false
                } else {
                    entry.result = infos.toList()
                    entry.completed = clock()
                    true
                }
            }
            if (accepted) updated()
        }
        return null
    }

    fun invalidate(predicate: (K) -> Boolean) {
        val removed = synchronized(entries) {
            val keys = entries.keys.filter(predicate)
            keys.mapNotNull { entries.remove(it)?.future }
        }
        removed.forEach { it.cancel(true) }
    }
}
