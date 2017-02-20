package body.core.safetyPromise;

import body.core.logger.Loggerable;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

/**
 * Created by vicboma on 08/02/17.
 * Mixin
 */
public interface SafetyPromiseAsync<T> extends Loggerable {

    static final AtomicInteger atomic = new AtomicInteger(1);
    static final ExecutorService executor = Executors.newCachedThreadPool(r -> new Thread(r, "Async thread: "+atomic.getAndIncrement()));

    /**
     * ForkJoinTask
     */
    default CompletableFuture<Void> executeRunnable(Runnable runnable) {
        getLogger().debug("Start executeRunnable(Runnable runnable) ");
        return CompletableFuture.runAsync(runnable);
    }

    /**
     * ForkJoinTask
     */
    default<T> CompletableFuture<T> executeSupply(Supplier<T> supplier) {
        getLogger().debug("Start executeSupply(Supplier<T> supplier)  ");
        return CompletableFuture.supplyAsync(supplier);
    }

    /**
     * newCachedThreadPool
     */
    default CompletableFuture<Void> executeRunnableCached(Runnable runnable) {
        getLogger().debug("Start executeRunnable(Runnable runnable) ");
        return CompletableFuture.runAsync(runnable,executor);
    }

    /**
     * newCachedThreadPool
     */
    default <T> CompletableFuture<T> executeSupplyCached(Supplier<T> supplier) {
        getLogger().debug("Start executeSupply(Supplier<T> supplier)  ");
        return CompletableFuture.supplyAsync(supplier,executor);
    }
}
