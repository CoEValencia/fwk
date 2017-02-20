package body.core.safetyPromise;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.function.Supplier;
import java.util.stream.IntStream;

/**
 * Created by vicboma on 10/02/17.
 */
public class SafetyPromiseAsyncTest implements SafetyPromiseAsync {

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
    public void testExecuteRunnable2() throws Exception {
       executeRunnable(()-> org.junit.Assert.assertTrue(true) )
               .thenRunAsync(()-> {
                    org.junit.Assert.assertTrue(!false);
                   latch.countDown();
               });

        latch.await();
    }

    @Test
    public void testExecuteSupply2() throws Exception {
        executeSupply(() -> false )
                .thenAcceptAsync(it ->{
                    org.junit.Assert.assertTrue(!(Boolean)it);
                    latch.countDown();
                });

        latch.await();
    }

    @Test
    public void testExecuteRunnableCached() throws Exception {
        IntStream.rangeClosed(0,999)
                 .forEach(it -> {
                     final CountDownLatch latchCached = new CountDownLatch(1);
                     executeRunnableCached(() -> {
                         getLogger().debug("Start executeRunnableCached "+it);
                         try {
                             if(it % 2 == 0)
                                 Thread.sleep(50);
                         } catch (InterruptedException e) {
                             getLogger().error("InterruptedException : "+e.getMessage());
                             e.printStackTrace();
                             org.junit.Assert.fail();
                         }
                     }).thenRunAsync(()-> {
                         org.junit.Assert.assertTrue(true);
                         latchCached.countDown();
                     });

                     try {
                         latchCached.await();
                     } catch (InterruptedException e) {
                         e.printStackTrace();
                     }

                 });
    }

    @Test
    public void testExecuteSupplyCached() throws Exception {
        IntStream.rangeClosed(0,999)
                .forEach(it -> {
                    final CountDownLatch latchCached = new CountDownLatch(1);
                    this.executeSupplyCached((Supplier<Boolean>) () -> {
                        getLogger().debug("Start executeSupplyCached "+it);
                        try {
                            if(it % 2 == 0)
                                Thread.sleep(50);
                        } catch (InterruptedException e) {
                            getLogger().error("InterruptedException : "+e.getMessage());
                            e.printStackTrace();
                            org.junit.Assert.fail();
                        }
                        finally {
                            return Boolean.TRUE;
                        }
                    }).thenAcceptAsync( res -> {
                        org.junit.Assert.assertTrue((Boolean)res);
                        latchCached.countDown();
                    });

                    try {
                        latchCached.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                });
    }

}