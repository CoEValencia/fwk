package body.core.lifecycle;

import body.core.lifecycleState.LifecycleState;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public class LifecycleAsyncImpl implements LifecycleAsync {

    private String state;

    public static LifecycleAsync create() {
        return new LifecycleAsyncImpl();
    }


    LifecycleAsyncImpl() {
        state = LifecycleState.NO_STATE;
    }

    @Override
    public Boolean isInitialize() {
        return (state == LifecycleState.INITIALIZE);
    }

    @Override
    public Boolean isPreDestroy() {
        return (state == LifecycleState.DESTROY);
    }

    @Override
    public Boolean isPostConstructor() {
        return (state == LifecycleState.CONSTRUCTOR);
    }

    @Override
    public Boolean isResume() {
        return (state == LifecycleState.RESUME);
    }

    @Override
    public <H, T> CompletableFuture<T> initialize(Function<H, T> fun) {
        return null;
    }

    @Override
    public <H, T> CompletableFuture<T> preDestroy(Function<H, T> fun) {
        return null;
    }

    @Override
    public <H, T> CompletableFuture<T> postConstructor(Function<H, T> fun) {
        return null;
    }

    @Override
    public <H, T> CompletableFuture<T> resume(Function<H, T> fun) {
        return null;
    }
}
