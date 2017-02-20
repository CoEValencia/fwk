package body.core.safetyPromise;

import body.core.logger.Loggerable;

import java.util.concurrent.*;
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


    default CompletableFuture<Void> executeRunnable(Runnable runnable, Executor executor) {
        //getLogger().debug("Start executeRunnableCached(Runnable runnable, Executor executor) ");
        return CompletableFuture.runAsync(runnable,executor);
    }

   default <T> CompletableFuture<T> executeSupply(Supplier<T> supplier,Executor executor) {
        //getLogger().debug("Start executeSupplyCached(Supplier<T> supplier, Executor executor)  ");
        return CompletableFuture.supplyAsync(supplier,executor);
    }
}
