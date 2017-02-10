package body.core.safetyPromise;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;

/**
 * Created by vicboma on 10/02/17.
 */
public class SafetyPromiseAsyncTest implements SafetyPromiseAsync {

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testExecuteRunnable() throws Exception {
        final CompletableFuture<Boolean> completableFuture = CompletableFuture.completedFuture(true);

        completableFuture.thenAcceptAsync(it ->{
            org.junit.Assert.assertTrue(true);
        });

       executeRunnable(completableFuture);
    }

    @Test
    public void testExecuteSupply() throws Exception {
        final CompletableFuture<Boolean> completableFuture = CompletableFuture.completedFuture(false);

        executeSupply(completableFuture)
                .thenAcceptAsync(it ->{
            org.junit.Assert.assertTrue(!(boolean)it);
        });

    }
}