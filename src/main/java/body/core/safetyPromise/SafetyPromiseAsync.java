package body.core.safetyPromise;

import body.core.logger.Loggerable;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.Supplier;

import static java.util.concurrent.CompletableFuture.completedFuture;
import static java.util.concurrent.CompletableFuture.supplyAsync;

/**
 * Created by vicboma on 08/02/17.
 * Mixin
 */
public interface SafetyPromiseAsync<T> extends Loggerable {

    /**
     * ForkJoinTask
     */
    default CompletableFuture<Void> executeRunnable(Runnable runnable) {
        //getLogger().debug("Start executeRunnable(Runnable runnable) ");
        return CompletableFuture.runAsync(runnable);
    }

    /**
     * ForkJoinTask
     */
    default<T> CompletableFuture<T> executeSupply(Supplier<T> supplier) {
        //getLogger().debug("Start executeSupply(Supplier<T> supplier)  ");
        return CompletableFuture.supplyAsync(supplier);
    }

    default<K,V> CompletableFuture<V> executeFunction(Function<K,V> func, K key) {
        //getLogger().debug("Start executeFunction(Function<K,V> func)  ");
        return CompletableFuture.completedFuture(func.apply(key));
    }

    default CompletableFuture<Void> executeRunnable(Runnable runnable, Executor executor) {
        //getLogger().debug("Start executeRunnableCached(Runnable runnable, Executor executor) ");
        return CompletableFuture.runAsync(runnable,executor);
    }

   default <T> CompletableFuture<T> executeSupply(Supplier<T> supplier,Executor executor) {
        //getLogger().debug("Start executeSupplyCached(Supplier<T> supplier, Executor executor)  ");
        return CompletableFuture.supplyAsync(supplier,executor);
    }
}
