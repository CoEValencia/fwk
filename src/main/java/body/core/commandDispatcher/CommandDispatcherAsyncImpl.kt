package body.core.commandDispatcher

import java.util.HashSet
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

    override fun add(command: String, completableList: List<CompletableFuture<T>>): CommandDispatcherAsync<T> {
        completableList
                .stream()
                .filter { it -> it != null }
                .forEach { it -> add(command, it) }

        return this
    }

    override fun add(command: String, completable: CompletableFuture<T>): CommandDispatcherAsync<T> {
        val listFun = map.get(command)

        when (listFun) {
            null -> map.put(command, arrayListOf(completable))
            else -> {
                if (!listFun.contains(completable))
                    listFun.add(completable)
            }
        }

        return this
    }

    override fun contains(command: String) = map.containsKey(command)

    override fun remove(command: String, completable: CompletableFuture<T>): CommandDispatcherAsync<T> {
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

    override fun remove(command: String): CommandDispatcherAsync<T> {
        map.remove(command)
        return this
    }

    override fun dispatch(command: String): CommandDispatcherAsync<T> {
        val listFun = map.get(command)
        if (null != listFun) {
            for (i in listFun!!.indices) {
                val res = intArrayOf(i)
                executeSupply(listFun!!.get(res[0]))
            }
        }

        return this
    }

    override fun dispatchOnce(command: String): CommandDispatcherAsync<T> {
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

    override fun dispatchAll(command: String): CompletableFuture<Void> {
        val child = map.get(command)
        val toArray = child?.toTypedArray()
        if(toArray?.size!! <= 0)
            return CompletableFuture.completedFuture(null)

        val res = hashSetOf<CompletableFuture<T>>()
        return CompletableFuture.runAsync {
            logger.debug("Start dispatchAll ")
            while(res.size != toArray?.size) {
                toArray?.
                    filter { it.isDone }?.
                    forEach {
                        res.add(it)
                }
            }
            res.forEach { logger.debug("${it.toString()}") }
            logger.debug("End dispatchAll ")
        }
    }

    override fun <H> dispatchAny(command: String) : CompletableFuture<H> {
        val child = map.get(command)
        val toArray = child?.toTypedArray()
        if(toArray?.size!! <= 0)
            return CompletableFuture.completedFuture(null)

        val res = CompletableFuture<H>()
        CompletableFuture.runAsync {
            logger.debug("Start dispatchAny ")
            while (!res.isDone) {
                toArray?.
                        filter { it.isDone }?.
                        forEach {

                            when (!res.isDone) {
                                true -> {
                                    logger.debug("${res.toString()}")
                                    res.complete(it.get() as (H))
                                    logger.debug("${res.toString()}")
                                }
                                else -> {
                                }
                            }
                        }
            }
            logger.debug("End dispatchAny ")
        }

        return res
    }

    override fun size() = this.map.size

}
