package body.core.commandDispatcher;

import body.core.logger.Loggerable;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by vicboma on 10/02/17.
 */
public class CommandDispatcherAsyncImplTest implements Loggerable{

    private CountDownLatch latch;
    private CommandDispatcherAsync commandDispatcherAsync;

    @Before
    public void setUp() throws Exception {
        commandDispatcherAsync = CommandDispatcherAsyncImpl.Companion.create();
        latch = new CountDownLatch(1);
    }

    @After
    public void tearDown() throws Exception {
        commandDispatcherAsync = null;
        latch = null;
    }

    @Test
    public void testAddContains() throws Exception {
        final CompletableFuture<Boolean> completableFuture = new CompletableFuture();
        String testCommand = "testCommand";
        final boolean res = commandDispatcherAsync
                                    .add(testCommand,completableFuture)
                                    .contains(testCommand);
        Assert.assertTrue(res);
    }

    private String testCommand = "testCommand";
    private CompletableFuture<Boolean> completableFuture;

    @Test
    public void testContainsNotRepeats() throws Exception {
        final int expectedParent = 2;
        completableFuture = new CompletableFuture<>();
        String testCommand2 = "testCommand2";

        commandDispatcherAsync
                .add(testCommand,completableFuture)
                .add(testCommand2,completableFuture)
                .add(testCommand,completableFuture);

        final int sizeParent = commandDispatcherAsync.size();

        Assert.assertTrue(sizeParent == expectedParent);
    }

    @Test
    public void testContainsNotListRepeats() throws Exception {
        final int expectedParent = 2;
        completableFuture = CompletableFuture.completedFuture(true);
        String testCommand2 = "testCommand2";

        commandDispatcherAsync
                .add(testCommand, Arrays.asList(completableFuture,completableFuture))
                .add(testCommand2,completableFuture);

        final int sizeParent = commandDispatcherAsync.size();

        Assert.assertTrue(sizeParent == expectedParent);
    }

    @Test
    public void testRemove() throws Exception {
        final int expectedParent = 1;
        testContainsNotRepeats();
        commandDispatcherAsync.remove(testCommand);
        final int sizeParent = commandDispatcherAsync.size();
        Assert.assertTrue(sizeParent == expectedParent);

    }

    @Test
    public void testRemoveChild() throws Exception {
        final int expectedParent = 2;
        testContainsNotRepeats();
        commandDispatcherAsync.remove(testCommand,completableFuture);
        final int sizeParent = commandDispatcherAsync.size();
        Assert.assertTrue(sizeParent == expectedParent);

    }

    @Test
    public void testSize() throws Exception {
        final int expectedParent = 3;
        Assert.assertTrue(0 == commandDispatcherAsync.size());
        completableFuture = new CompletableFuture<>();

        commandDispatcherAsync
                .add(testCommand,completableFuture)
                .add("testCommand2",completableFuture)
                .add("testCommand3",completableFuture);

        final int sizeParent = commandDispatcherAsync.size();

        Assert.assertTrue(sizeParent == expectedParent);
    }

    @Test
    public void testDispatch() throws Exception {
         final CompletableFuture<Callable<Boolean>> completableFuture = new CompletableFuture();

        completableFuture.thenAcceptAsync(it ->{
            try {
                Assert.assertTrue(it.call());
            } catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                latch.countDown();
            }
        });

        commandDispatcherAsync.dispatch(testCommand);

        completableFuture.complete(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            finally {
                return Boolean.TRUE;
            }
        });


        latch.await();
    }

    @Test
    public void testDispatch2() throws Exception {
        final CompletableFuture<Boolean> completableFuture = new CompletableFuture();
        completableFuture.thenAcceptAsync(it -> {
            Assert.assertTrue(it);
            latch.countDown();
        });

        commandDispatcherAsync
                .add(testCommand,completableFuture)
                .dispatch(testCommand);

        completableFuture.complete(true);

        latch.await();
    }

    @Test
    public void testDispatchCancel() throws Exception {
        final CompletableFuture<Boolean> completableFuture = new CompletableFuture();

        commandDispatcherAsync
                .add(testCommand,completableFuture)
                .dispatch(testCommand);

        completableFuture.thenAcceptAsync(it ->{
                Assert.assertTrue(it);
             });

        completableFuture.cancel(true);
        completableFuture.complete(false);
    }

    @Test
    public void testDispatchOnce() throws Exception {
        final CompletableFuture<CommandDispatcherAsync> completableFuture = new CompletableFuture();

        completableFuture.thenAcceptAsync(it ->{
            Assert.assertEquals(Integer.valueOf(0), it.size());
            latch.countDown();
        });

        commandDispatcherAsync.add(testCommand,completableFuture);

        Assert.assertEquals(Integer.valueOf(1), commandDispatcherAsync.size());

        commandDispatcherAsync.dispatchOnce(testCommand);

        completableFuture.complete(commandDispatcherAsync);

        latch.await();
    }

    @Test
    public void testDispatchAll() throws Exception {
        final int result[] = new int[1];
        final int expected = 2;
        final CompletableFuture<Integer> completableFuture1 = new CompletableFuture();
        final CompletableFuture<Integer> completableFuture2 = new CompletableFuture();

        commandDispatcherAsync
                .add(testCommand,completableFuture1)
                .add(testCommand,completableFuture2)
                .dispatchAll(testCommand)
                .thenRunAsync(() ->{
                    getLogger().debug("thenRunAsync: ");
                    Assert.assertEquals(expected,result[0]);
                    latch.countDown();
                    getLogger().debug("latch.countDown()");
                });

        completableFuture1.thenAcceptAsync(it ->{
            result[0]+=it;
            getLogger().debug("completableFuture1.thenAcceptAsync: "+result[0]);
        });

        completableFuture2.thenAcceptAsync(it ->{
            result[0]+=it;
            getLogger().debug("completableFuture2.thenAcceptAsync: "+result[0]);
        });


        completableFuture1.complete(1);
        completableFuture2.complete(1);

        latch.await();

        getLogger().debug("End dispatcherAll test");

    }

    @Test
    public void testDispatchAny() throws Exception {
        final int result[] = new int[1];
        final int expected = 1;
        final CompletableFuture<Integer> completableFuture1 = new CompletableFuture();
        final CompletableFuture<Integer> completableFuture2 = new CompletableFuture();
        final CompletableFuture<Integer> completableFuture3 = new CompletableFuture();


        commandDispatcherAsync
                .add(testCommand,completableFuture1)
                .add(testCommand,completableFuture2)
                .add(testCommand,completableFuture3)
                .dispatchAny(testCommand)
                .thenAcceptAsync( it ->{
                    getLogger().debug("thenRunAsync: "+it);
                    Assert.assertEquals(expected,result[0]);
                    latch.countDown();
                    getLogger().debug("latch.countDown()");
                });

        completableFuture1.thenAcceptAsync(it ->{
            result[0] += it;
            getLogger().debug("completableFuture1.thenAcceptAsync: "+result[0]);
        });

        completableFuture2.thenAcceptAsync(it ->{
            result[0] += it;
            getLogger().debug("completableFuture2.thenAcceptAsync: "+result[0]);
        });

        completableFuture3.thenAcceptAsync(it ->{
            result[0] += it;
            getLogger().debug("completableFuture3.thenAcceptAsync: "+result[0]);
        });

        completableFuture1.complete(Integer.valueOf(1));
        Thread.sleep(2000);
        completableFuture2.complete(Integer.valueOf(1));
        completableFuture3.complete(Integer.valueOf(1));

        latch.await();

        getLogger().debug("End dispatcherAny test");

    }
}
