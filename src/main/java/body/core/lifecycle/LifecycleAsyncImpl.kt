package body.core.lifecycle

import body.core.lifecycleState.LifecycleState

import java.util.concurrent.CompletableFuture
import java.util.function.Function

class LifecycleAsyncImpl<T> internal constructor(private val obj: T, private var state: String) : LifecycleAsync<T> {

    init {
        this.state = LifecycleState.CONSTRUCTOR
    }

    companion object {
        fun <H> create(obj: H): LifecycleAsync<H>  = LifecycleAsyncImpl(obj, LifecycleState.NO_STATE)
    }

    override fun getObject() = this.obj

    override fun getState() =  this.state

    override fun isInitialize() = (this.state === LifecycleState.INITIALIZE)

    override fun isPreDestroy() = (this.state === LifecycleState.DESTROY)

    override fun isPostConstructor() = (this.state === LifecycleState.CONSTRUCTOR)

    override fun isResume() = (this.state === LifecycleState.RESUME)

    override fun isNotState() = (this.state === LifecycleState.NO_STATE)



    override fun <H, T> initialize(`fun`: Function<H, T>): CompletableFuture<T>? {
        return null
    }

    override fun <H, T> preDestroy(`fun`: Function<H, T>): CompletableFuture<T>? {
        return null
    }

    override fun <H, T> postConstructor(`fun`: Function<H, T>): CompletableFuture<T>? {
        return null
    }

    override fun <H, T> resume(`fun`: Function<H, T>): CompletableFuture<T>? {
        return null
    }



}
