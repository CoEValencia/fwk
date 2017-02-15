package body.core.safetyPromise;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;

/**
 * Created by vicboma on 10/02/17.
 */
public class SafetyPromiseAsyncTest implements SafetyPromiseAsync<Boolean> {

    private CountDownLatch latch;

    @Before
    public void setUp() throws Exception {
        latch = new CountDownLatch(1);
    }

    @After
    public void tearDown() throws Exception {
        latch = null;
    }

    @Test
    public void testExecuteRunnable() throws Exception {
        final CompletableFuture<Boolean> completableFuture = CompletableFuture.completedFuture(true);

        completableFuture.thenAcceptAsync(it ->{
            org.junit.Assert.assertTrue(true);
            latch.countDown();
        });

       executeRunnable(completableFuture);
       latch.await();
    }

    @Test
    public void testExecuteRunnable2() throws Exception {
       executeRunnable(()-> org.junit.Assert.assertTrue(true) )
               .thenRunAsync(()-> {
                    org.junit.Assert.assertTrue(!false);
                   latch.countDown();
               });

        latch.await();
    }

    @Test
    public void testExecuteSupply() throws Exception {
        final CompletableFuture<Boolean> completableFuture = new CompletableFuture<>();
        executeSupply(completableFuture)
                .thenAcceptAsync(it -> {
                    org.junit.Assert.assertTrue(it);
                    latch.countDown();
                });

        completableFuture.complete(true);
        latch.await();
    }

    @Test
    public void testExecuteSupply2() throws Exception {
        executeSupply(()-> false )
                .thenAcceptAsync(it ->{
                    org.junit.Assert.assertTrue(!it);
                    latch.countDown();
                });

        latch.await();
    }
}