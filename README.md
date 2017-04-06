# Framework Async - WIP

## API

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
    
    fun dispatch(command: String, executor: Executor): CommandDispatcherAsync<T>
    
    fun dispatchOnce(command: String,executor: Executor): CommandDispatcherAsync<T>

    fun dispatchAll(command: String,executor: Executor): CompletableFuture<Void>

    fun <T> dispatchAny(command: String,executor: Executor): CompletableFuture<T>

    fun size() : Int
```

### Safety Promise Async | [src](https://github.com/CoEValencia/fwk/blob/master/src/main/java/body/core/safetyPromise/SafetyPromiseAsync.java) - [test](https://github.com/CoEValencia/fwk/blob/master/src/test/java/body/core/safetyPromise/SafetyPromiseAsyncTest.java)
```java

    default CompletableFuture<Void> executeRunnable(Runnable runnable);

    default<T> CompletableFuture<T> executeSupply(Supplier<T> supplier);

    default CompletableFuture<Void> executeRunnable(Runnable runnable, executor: Executor);

    default <T> CompletableFuture<T> executeSupply(Supplier<T> supplier, executor: Executor);
```

### Logger | [src](https://github.com/CoEValencia/fwk/blob/master/src/main/java/body/core/logger/Loggerable.java) - [test](https://github.com/CoEValencia/fwk/blob/master/src/test/java/body/core/logger/LoggerableTest.java)
```java

    default void loggerTrace(Object str);

    default void loggerDebug(Object str);

    default void loggerInfo(Object str);

    default void loggerError(Object str);
```


@Author : Victor Bolinches Marin

@Author : [vicboma1](https://www.google.es/#q=vicboma1)

