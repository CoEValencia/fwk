package body.core.commandDispatcher;

import java.util.concurrent.CompletableFuture;


public interface CommandDispatcherAsync<T> {

    CommandDispatcherAsyncImpl add(String command, CompletableFuture<T> fun);

    Boolean contains(String command);

    CommandDispatcherAsyncImpl remove(String command, CompletableFuture<T> fun);

    CompletableFuture dispatch(String command, CompletableFuture<T> fun);
}
