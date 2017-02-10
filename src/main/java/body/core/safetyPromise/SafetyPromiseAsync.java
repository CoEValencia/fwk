package body.core.safetyPromise;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Created by vicboma on 08/02/17.
 */
public interface SafetyPromiseAsync<T> {

    default void executeRunnable(CompletableFuture<T> promise) {
        CompletableFuture.runAsync(() -> {
            try {
                promise.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        } );
    }

    default CompletableFuture<CompletableFuture<T>> executeSupply(CompletableFuture<T> promise) {
        return CompletableFuture.supplyAsync(() -> promise );
    }
}
