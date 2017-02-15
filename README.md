# Framework Async - WIP

##API

### Command Dispatcher Async | [src](https://github.com/CoEValencia/fwk/blob/master/src/main/java/body/core/commandDispatcher/CommandDispatcherAsyncImpl.kt) - [test](https://github.com/CoEValencia/fwk/blob/master/src/test/java/body/core/commandDispatcher/CommandDispatcherAsyncImplTest.java)
```kotlin

    fun add(command : String , callbackList : kotlin.collections.List<CompletableFuture<T>> ) : CommandDispatcherAsync<T>
    
    fun add(command : String, callback : CompletableFuture<T> ) : CommandDispatcherAsync<T>

    fun contains(command : String) : Boolean

    fun remove(command : String, callback : CompletableFuture<T>) : CommandDispatcherAsync<T>

    fun remove(command : String) : CommandDispatcherAsync<T>

    fun dispatch(command : String) : CommandDispatcherAsync<T>

    fun dispatchOnce(command : String) : CommandDispatcherAsync<T>

    fun dispatchAll(command : String) :  CompletableFuture<Void>

    fun <T> dispatchAny(command : String) : CompletableFuture<T>

    fun size() : Int
```

### Safety Promise Async | [src](https://github.com/CoEValencia/fwk/blob/master/src/main/java/body/core/safetyPromise/SafetyPromiseAsync.java) - [test](https://github.com/CoEValencia/fwk/blob/master/src/test/java/body/core/safetyPromise/SafetyPromiseAsyncTest.java)
```java

    default <T> CompletableFuture<Void> executeRunnable(CompletableFuture<T> promise);

    default CompletableFuture<Void> executeRunnable(Runnable runnable);

    default CompletableFuture<T> executeSupply(CompletableFuture<T> promise);

    default <T> CompletableFuture<T> executeSupply(Supplier<T> supplier) ;
```

### Logger | [src](https://github.com/CoEValencia/fwk/blob/master/src/main/java/body/core/logger/Loggerable.java) - [test](https://github.com/CoEValencia/fwk/blob/master/src/test/java/body/core/logger/LoggerableTest.java)
```java

    default void loggerTrace(Object str);

    default void loggerDebug(Object str);

    default void loggerInfo(Object str);

    default void loggerError(Object str);
```


@Victor Bolinches Marin

@vicboma1
