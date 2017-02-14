package body.core.commandDispatcher;

import body.core.safetyPromise.SafetyPromiseAsync;

import java.util.List;
import java.util.concurrent.CompletableFuture;


public interface CommandDispatcherAsync<T> extends SafetyPromiseAsync<T> {

    CommandDispatcherAsyncImpl<T> add(String command, List<CompletableFuture<T>> fun);

    CommandDispatcherAsyncImpl<T> add(String command, CompletableFuture<T> fun);

    Boolean contains(String command);

    CommandDispatcherAsyncImpl<T> remove(String command, CompletableFuture<T> fun);

    CommandDispatcherAsyncImpl<T> remove(String command);

    CommandDispatcherAsyncImpl<T> dispatch(String command);

    CommandDispatcherAsyncImpl<T> dispatchOnce(String command);

    CompletableFuture<Void> dispatchAll(String command);

    Integer size();
}
