# FrameworkAsync - WIP


### Command Dispatcher Async
```

    CommandDispatcherAsyncImpl<T> add(String command, List<CompletableFuture<T>> fun);

    CommandDispatcherAsyncImpl<T> add(String command, CompletableFuture<T> fun);

    Boolean contains(String command);

    CommandDispatcherAsyncImpl<T> remove(String command, CompletableFuture<T> fun);

    CommandDispatcherAsyncImpl<T> remove(String command);

    CommandDispatcherAsyncImpl<T> dispatch(String command);
    
    CommandDispatcherAsyncImpl<T> dispatchOne(String command);

    CompletableFuture<Void> dispatchAll(String command);

    Integer size();
```