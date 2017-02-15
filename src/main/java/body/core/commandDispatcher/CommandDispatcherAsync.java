package body.core.commandDispatcher;

import body.core.safetyPromise.SafetyPromiseAsync;

import java.util.List;
import java.util.concurrent.CompletableFuture;


public interface CommandDispatcherAsync<T> extends SafetyPromiseAsync<T> {

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
}
