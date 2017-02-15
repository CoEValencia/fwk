package body.core.safetyPromise;

import body.core.logger.Loggerable;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

/**
 * Created by vicboma on 08/02/17.
 * Trait
 */
public interface SafetyPromiseAsync<T> extends Loggerable {

    default CompletableFuture<Void> executeRunnable(CompletableFuture<T> promise) {
        getLogger().debug("Start executeRunnable(CompletableFuture<T> promise) ");
        return CompletableFuture.runAsync(() -> {
            try {
                promise.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        } );
    }

    default CompletableFuture<Void> executeRunnable(Runnable runnable) {
        getLogger().debug("Start executeRunnable(Runnable runnable) ");
        return CompletableFuture.runAsync(runnable);
    }

    default <T> CompletableFuture<T> executeSupply(CompletableFuture<T> promise) {
        getLogger().debug("Start executeSupply(CompletableFuture<T> promise) ");
        return CompletableFuture.supplyAsync(() -> {
                    T data = null;
                    try {
                        getLogger().debug("Start promise.get ");
                        data = promise.get();
                        getLogger().debug("End promise.get "+data);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }finally {
                        getLogger().debug("return promise.get");
                        return data;
                    }
                }
        );
    }

    default<T> CompletableFuture<T> executeSupply(Supplier<T> supplier) {
        getLogger().debug("Start executeSupply(Supplier<T> supplier)  ");
        return CompletableFuture.supplyAsync(supplier);
    }
}
