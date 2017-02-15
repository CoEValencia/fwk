# Framework Async - WIP

##API

### Command Dispatcher Async | [src](https://github.com/CoEValencia/fwk/blob/master/src/main/java/body/core/commandDispatcher/CommandDispatcherAsyncImpl.kt) - [test](https://github.com/CoEValencia/fwk/blob/master/src/test/java/body/core/commandDispatcher/CommandDispatcherAsyncImplTest.java)
```
    CommandDispatcherAsync<T> add(String command, List<CompletableFuture<T>> fun);

    CommandDispatcherAsync<T> add(String command, CompletableFuture<T> fun);

    Boolean contains(String command);

    CommandDispatcherAsync<T> remove(String command, CompletableFuture<T> fun);

    CommandDispatcherAsync<T> remove(String command);

    CommandDispatcherAsync<T> dispatch(String command);

    CommandDispatcherAsync<T> dispatchOnce(String command);

    CompletableFuture<Void> dispatchAll(String command);

    <T> CompletableFuture<T> dispatchAny(String command);

    Integer size();
```

### Safety Promise Async | [src](https://github.com/CoEValencia/fwk/blob/master/src/main/java/body/core/safetyPromise/SafetyPromiseAsync.java) - [test](https://github.com/CoEValencia/fwk/blob/master/src/test/java/body/core/safetyPromise/SafetyPromiseAsyncTest.java)
```
    default <T> CompletableFuture<Void> executeRunnable(CompletableFuture<T> promise);

    default CompletableFuture<Void> executeRunnable(Runnable runnable);

    default CompletableFuture<T> executeSupply(CompletableFuture<T> promise);

    default <T> CompletableFuture<T> executeSupply(Supplier<T> supplier) ;
```

### Logger | [src](https://github.com/CoEValencia/fwk/blob/master/src/main/java/body/core/logger/Loggerable.java) - [test](https://github.com/CoEValencia/fwk/blob/master/src/test/java/body/core/logger/LoggerableTest.java)
```
    default void loggerTrace(Object str);

    default void loggerDebug(Object str);

    default void loggerInfo(Object str);

    default void loggerError(Object str);
```


@Victor Bolinches Marin

@vicboma1
