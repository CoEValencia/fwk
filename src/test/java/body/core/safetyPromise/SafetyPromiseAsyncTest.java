package body.core.safetyPromise;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
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
        IntStream.rangeClosed(0,60)
                 .forEach(it -> {
                     final CountDownLatch latchCached = new CountDownLatch(1);
                     executeRunnable(() -> {
                         //getLogger().debug("Start executeRunnableCached "+it);
                         try {
                             if(it % 2 == 0)
                                 Thread.sleep(50);
                         } catch (InterruptedException e) {
                             getLogger().error("InterruptedException : "+e.getMessage());
                             e.printStackTrace();
                             org.junit.Assert.fail();
                         }
                     }, Executors.newCachedThreadPool())
                             .thenRunAsync(()-> {
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
        IntStream.rangeClosed(0,60)
                .forEach(it -> {
                    final CountDownLatch latchCached = new CountDownLatch(1);
                    this.executeSupply((Supplier<Boolean>) () -> {
                        //getLogger().debug("Start executeSupplyCached "+it);
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
                    }, Executors.newCachedThreadPool())
                            .thenAcceptAsync( res -> {
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

    @Test
    public void testExecuteRunnableFixed() throws Exception {
        IntStream.rangeClosed(0,60)
                .forEach(it -> {
                    final CountDownLatch latchCached = new CountDownLatch(1);
                    executeRunnable(() -> {
                        //getLogger().debug("Start testExecuteRunnableFixed "+it);
                        try {
                            if(it % 2 == 0)
                                Thread.sleep(50);
                        } catch (InterruptedException e) {
                            getLogger().error("InterruptedException : "+e.getMessage());
                            e.printStackTrace();
                            org.junit.Assert.fail();
                        }
                    }, Executors.newFixedThreadPool(1))
                            .thenRunAsync(()-> {
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
    public void testExecuteSupplyFixed() throws Exception {
        IntStream.rangeClosed(0,60)
                .forEach(it -> {
                    final CountDownLatch latchCached = new CountDownLatch(1);
                    this.executeSupply((Supplier<Boolean>) () -> {
                        //getLogger().debug("Start testExecuteSupplyFixed "+it);
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
                    }, Executors.newFixedThreadPool(1))
                            .thenAcceptAsync( res -> {
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

    @Test
    public void testExecuteRunnableScheduled() throws Exception {
        IntStream.rangeClosed(0,60)
                .forEach(it -> {
                    final CountDownLatch latchCached = new CountDownLatch(1);
                    executeRunnable(() -> {
                        //getLogger().debug("Start testExecuteRunnableScheduled "+it);
                        try {
                            if(it % 2 == 0)
                                Thread.sleep(50);
                        } catch (InterruptedException e) {
                            getLogger().error("InterruptedException : "+e.getMessage());
                            e.printStackTrace();
                            org.junit.Assert.fail();
                        }
                    }, Executors.newScheduledThreadPool(1))
                            .thenRunAsync(()-> {
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
    public void testExecuteSupplyScheduled() throws Exception {
        IntStream.rangeClosed(0,60)
                .forEach(it -> {
                    final CountDownLatch latchCached = new CountDownLatch(1);
                    this.executeSupply((Supplier<Boolean>) () -> {
                        //getLogger().debug("Start testExecuteSupplyScheduled "+it);
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
                    }, Executors.newScheduledThreadPool(1))
                            .thenAcceptAsync( res -> {
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

    @Test
    public void testExecuteRunnableNewWorkStealingPool() throws Exception {
        IntStream.rangeClosed(0,60)
                .forEach(it -> {
                    final CountDownLatch latchCached = new CountDownLatch(1);
                    executeRunnable(() -> {
                        //getLogger().debug("Start testExecuteRunnableNewWorkStealingPool "+it);
                        try {
                            if(it % 2 == 0)
                                Thread.sleep(50);
                        } catch (InterruptedException e) {
                            getLogger().error("InterruptedException : "+e.getMessage());
                            e.printStackTrace();
                            org.junit.Assert.fail();
                        }
                    }, Executors.newWorkStealingPool())
                            .thenRunAsync(()-> {
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
    public void testExecuteSupplyNewWorkStealingPool() throws Exception {
        IntStream.rangeClosed(0,60)
                .forEach(it -> {
                    final CountDownLatch latchCached = new CountDownLatch(1);
                    this.executeSupply((Supplier<Boolean>) () -> {
                        //getLogger().debug("Start testExecuteSupplyNewWorkStealingPool "+it);
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
                    }, Executors.newWorkStealingPool())
                            .thenAcceptAsync( res -> {
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