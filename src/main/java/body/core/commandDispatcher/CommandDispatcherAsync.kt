package body.core.commandDispatcher

import body.core.safetyPromise.SafetyPromiseAsync
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executor


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

    fun dispatch(command: String, executor: Executor): CommandDispatcherAsync<T>

    fun dispatchOnce(command: String,executor: Executor): CommandDispatcherAsync<T>

    fun dispatchAll(command: String,executor: Executor): CompletableFuture<Void>

    fun <T> dispatchAny(command: String,executor: Executor): CompletableFuture<T>

    fun size(): Int
}
