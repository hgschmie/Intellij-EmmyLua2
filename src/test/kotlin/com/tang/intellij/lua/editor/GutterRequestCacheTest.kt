package com.tang.intellij.lua.editor

import com.cppcxy.ide.lsp.GutterInfo
import org.junit.Assert.*
import org.junit.Test
import java.util.concurrent.CompletableFuture

class GutterRequestCacheTest {
    @Test fun deduplicatesPendingRequestsAndCachesEmptyReplies() {
        val cache = GutterRequestCache<String>()
        val pending = CompletableFuture<List<GutterInfo>>()
        var loads = 0
        var updates = 0
        val load = { loads++; pending }
        assertNull(cache.getOrRequest("file", 1, load) { updates++ })
        assertNull(cache.getOrRequest("file", 1, load) { updates++ })
        assertEquals(1, loads)
        pending.complete(emptyList())
        assertEquals(emptyList<GutterInfo>(), cache.getOrRequest("file", 1, load) { updates++ })
        assertEquals(1, loads)
        assertEquals(1, updates)
    }

    @Test fun staleRepliesCannotReplaceANewerRevision() {
        val cache = GutterRequestCache<String>()
        // Simulate a server future that cannot actually be cancelled.
        val old = object : CompletableFuture<List<GutterInfo>>() {
            override fun cancel(mayInterruptIfRunning: Boolean) = false
        }
        val current = CompletableFuture<List<GutterInfo>>()
        var updates = 0
        cache.getOrRequest("file", 1, { old }) { updates++ }
        cache.getOrRequest("file", 2, { current }) { updates++ }
        old.complete(emptyList())
        assertEquals(0, updates)
        current.complete(emptyList())
        assertEquals(1, updates)
    }

    @Test fun invalidationCancelsRequestsAndSuppressesUpdates() {
        val cache = GutterRequestCache<String>()
        val pending = CompletableFuture<List<GutterInfo>>()
        cache.getOrRequest("file", 1, { pending }) { fail("invalidated callback") }
        cache.invalidate { true }
        assertTrue(pending.isCancelled)
    }

    @Test fun invalidationDuringRequestCreationCancelsItsFuture() {
        val cache = GutterRequestCache<String>()
        val pending = CompletableFuture<List<GutterInfo>>()
        cache.getOrRequest("file", 1, { cache.invalidate { true }; pending }) { fail("invalidated callback") }
        assertTrue(pending.isCancelled)
    }

    @Test fun failuresCanBeRetriedAndExpiryStartsWhenTheReplyArrives() {
        var now = 0L
        val cache = GutterRequestCache<String> { now }
        val failed = CompletableFuture<List<GutterInfo>>()
        cache.getOrRequest("file", 1, { failed }) { fail("failed callback") }
        failed.completeExceptionally(IllegalStateException("disconnected"))
        val pending = CompletableFuture<List<GutterInfo>>()
        cache.getOrRequest("file", 1, { pending }) {}
        now = 3000
        pending.complete(emptyList())
        assertEquals(emptyList<GutterInfo>(), cache.getOrRequest("file", 1, { fail("fresh reply"); pending }) {})
        now = 6000
        val refreshed = CompletableFuture<List<GutterInfo>>()
        assertNull(cache.getOrRequest("file", 1, { refreshed }) {})
    }
}
