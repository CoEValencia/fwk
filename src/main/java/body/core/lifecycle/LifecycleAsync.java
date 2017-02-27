package body.core.lifecycle;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public interface LifecycleAsync<T> {

     T getObject();
    String getState();

    Boolean isInitialize();
    Boolean isPreDestroy();
    Boolean isPostConstructor();
    Boolean isResume();
    Boolean isNotState();

    <H,T> CompletableFuture<T> initialize(Function<H,T> fun);
    <H,T> CompletableFuture<T> preDestroy(Function<H,T> fun);
    <H,T> CompletableFuture<T> postConstructor(Function<H,T> fun);
    <H,T> CompletableFuture<T> resume(Function<H,T> fun);


}
