package body.core.lifecycle;

import body.core.lifecycleState.LifecycleState;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public class LifecycleAsyncImpl<T> implements LifecycleAsync {

    private String state;
    private T obj;

    public static <H> LifecycleAsync create(H obj) {
        return new LifecycleAsyncImpl<H>(obj);
    }


    LifecycleAsyncImpl(T obj) {
        this.state = LifecycleState.NO_STATE;
        this.obj = obj;
    }

    public T getObject() {
        return this.obj;
    }

    public String getState() {
        return this.state;
    }

    public Boolean isInitialize() {
        return (this.state == LifecycleState.INITIALIZE);
    }

    public Boolean isPreDestroy() {
        return (this.state == LifecycleState.DESTROY);
    }

    public Boolean isPostConstructor() {
        return (this.state == LifecycleState.CONSTRUCTOR);
    }

    public Boolean isResume() {
        return (this.state == LifecycleState.RESUME);
    }

    public <H, T> CompletableFuture<T> initialize(Function<H, T> fun) {
        return null;
    }

    public <H, T> CompletableFuture<T> preDestroy(Function<H, T> fun) {
        return null;
    }

    public <H, T> CompletableFuture<T> postConstructor(Function<H, T> fun) {
        return null;
    }

    public <H, T> CompletableFuture<T> resume(Function<H, T> fun)  {
        return null;
    }

}
