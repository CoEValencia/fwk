# Framework Async - WIP

##API

### Command Dispatcher Async | [src](https://github.com/CoEValencia/fwk/blob/master/src/main/java/body/core/commandDispatcher/CommandDispatcherAsyncImpl.kt) - [test](https://github.com/CoEValencia/fwk/blob/master/src/test/java/body/core/commandDispatcher/CommandDispatcherAsyncImplTest.java)
```

    CommandDispatcherAsyncImpl<T> add(String command, List<CompletableFuture<T>> fun);

    CommandDispatcherAsyncImpl<T> add(String command, CompletableFuture<T> fun);

    Boolean contains(String command);

    CommandDispatcherAsyncImpl<T> remove(String command, CompletableFuture<T> fun);

    CommandDispatcherAsyncImpl<T> remove(String command);

    CommandDispatcherAsyncImpl<T> dispatch(String command);
    
    CommandDispatcherAsyncImpl<T> dispatchOnce(String command);

    CompletableFuture<Void> dispatchAll(String command);

    Integer size();
```
