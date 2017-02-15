package body.core.commandDispatcher

import body.core.safetyPromise.SafetyPromiseAsync
import java.util.concurrent.CompletableFuture


interface CommandDispatcherAsync<T> : SafetyPromiseAsync<T> {

    fun add(command: String, callbackList: kotlin.collections.List<CompletableFuture<T>>): CommandDispatcherAsync<T>

    fun add(command: String, callback: CompletableFuture<T>): CommandDispatcherAsync<T>

    fun contains(command: String): Boolean

    fun remove(command: String, callback: CompletableFuture<T>): CommandDispatcherAsync<T>

    fun remove(command: String): CommandDispatcherAsync<T>

    fun dispatch(command: String): CommandDispatcherAsync<T>

    fun dispatchOnce(command: String): CommandDispatcherAsync<T>

    fun dispatchAll(command: String): CompletableFuture<Void>

    fun <T> dispatchAny(command: String): CompletableFuture<T>

    fun size(): Int
}
