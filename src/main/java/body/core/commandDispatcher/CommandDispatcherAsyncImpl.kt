package body.core.commandDispatcher

import java.util.concurrent.CompletableFuture
import java.util.concurrent.ConcurrentHashMap

/**
 * Created by vicboma on 08/02/17.
 */
class CommandDispatcherAsyncImpl<T> internal constructor() : CommandDispatcherAsync<T> {

    private val map by lazy { ConcurrentHashMap<String, MutableList<CompletableFuture<T>>>() }

    init {

    }

    companion object {
        fun <J> create(): CommandDispatcherAsync<J> = CommandDispatcherAsyncImpl()
    }

    override fun add(command: String, completableList: List<CompletableFuture<T>>): CommandDispatcherAsyncImpl<T> {
        completableList
                .stream()
                .filter { it -> it != null }
                .forEach { it -> add(command, it) }

        return this
    }

    override fun add(command: String, completable: CompletableFuture<T>): CommandDispatcherAsyncImpl<T> {
        val listFun = map.get(command)

        when (listFun) {
            null -> map.put(command, arrayListOf(completable))
            else -> {
                if (!listFun.contains(completable))
                    listFun.plusElement(completable)
            }
        }

        return this
    }

    override fun contains(command: String) = map.containsKey(command)

    override fun remove(command: String, completable: CompletableFuture<T>): CommandDispatcherAsyncImpl<T> {
        val listFun = map.get(command)
        when (listFun) {
            null -> {
            }
            else -> {
                val index = listFun.indexOf(completable)
                if (listFun.indexOf(completable) != -1) {
                    val completableFuture = listFun?.removeAt(index)
                    completableFuture?.cancel(true)

                    if (listFun?.size == 0)
                        listFun?.clear()
                }
            }
        }

        return this
    }

    override fun remove(command: String): CommandDispatcherAsyncImpl<T> {
        map.remove(command)
        return this
    }

    override fun dispatch(command: String): CommandDispatcherAsyncImpl<T> {
        val listFun = map.get(command)
        if (null != listFun) {
            for (i in listFun!!.indices) {
                val res = intArrayOf(i)
                executeSupply(listFun!!.get(res[0]))
            }
        }

        return this
    }

    override fun dispatchOne(command: String): CommandDispatcherAsyncImpl<T> {
        val listFun = map.get(command)
        if (null != listFun) {
            for (i in listFun!!.indices) {
                val res = intArrayOf(i)
                executeSupply(listFun!!.get(res[0]))
            }
            remove(command)
        }

        return this
    }

    override fun dispatchAll(command: String) = CompletableFuture.allOf(*map.get(command)!!.toTypedArray())

    override fun size() = this.map.size

}