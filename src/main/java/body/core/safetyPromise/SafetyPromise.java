package body.core.safetyPromise;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Created by vicboma on 08/02/17.
 */
public interface SafetyPromise<T> {

    default T execute(CompletableFuture<T> promise) throws ExecutionException, InterruptedException {
        return promise.get();
    }

}
