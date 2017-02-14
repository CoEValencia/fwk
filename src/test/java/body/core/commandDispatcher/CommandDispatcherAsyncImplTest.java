package body.core.commandDispatcher;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by vicboma on 10/02/17.
 */
public class CommandDispatcherAsyncImplTest {

    CommandDispatcherAsync commandDispatcherAsync;

    @Before
    public void setUp() throws Exception {
        commandDispatcherAsync = CommandDispatcherAsyncImpl.create();
    }

    @After
    public void tearDown() throws Exception {
        commandDispatcherAsync = null;
    }

    @Test
    public void testAdd() throws Exception {
        final CommandDispatcherAsync commandDispatcherAsync = mock(CommandDispatcherAsyncImpl.class);
        final CompletableFuture<Boolean> completableFuture = mock(CompletableFuture.class);
        String testCommand = "testCommand";
        commandDispatcherAsync.add(testCommand,completableFuture);
        verify(commandDispatcherAsync).add(testCommand,completableFuture);
    }

    @Test
    public void testContains() throws Exception {
        final CompletableFuture<Boolean> completableFuture = CompletableFuture.completedFuture(true);
        String testCommand = "testCommand";
        commandDispatcherAsync.add(testCommand,completableFuture);
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
        completableFuture = CompletableFuture.completedFuture(true);
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
    }

    @Test
    public void testDispatch2() throws Exception {
        final CompletableFuture<Boolean> completableFuture = new CompletableFuture();

        completableFuture.thenAcceptAsync(it ->{
            Assert.assertTrue(it);
        });

        commandDispatcherAsync
                .add(testCommand,completableFuture)
                .dispatch(testCommand);
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
    public void testDispatchOne() throws Exception {
        final CompletableFuture<CommandDispatcherAsync> completableFuture = new CompletableFuture();

        completableFuture.thenAcceptAsync(it ->{
            Assert.assertEquals(0,it.size());
        });

        commandDispatcherAsync.add(testCommand,completableFuture);

        Assert.assertEquals(1,commandDispatcherAsync.size());

        commandDispatcherAsync.dispatchOne(testCommand);

        completableFuture.complete(commandDispatcherAsync);
    }

    @Test
    public void testDispatchAll() throws Exception {
        int result[] = new int[0];
        final int expected = 2;
        final CompletableFuture<CommandDispatcherAsync> completableFuture1 = new CompletableFuture();
        final CompletableFuture<CommandDispatcherAsync> completableFuture2 = new CompletableFuture();
        final CompletableFuture<CommandDispatcherAsync> completableFuture3 = new CompletableFuture();


        commandDispatcherAsync
                .add(testCommand,completableFuture1)
                .add(testCommand,completableFuture2)
                .add("testCommand3",completableFuture3);

        Assert.assertEquals(2,commandDispatcherAsync.size());

        commandDispatcherAsync
                .dispatchAll(testCommand)
                .thenAcceptAsync(it ->{
            Assert.assertEquals(expected,result);
        });

        completableFuture1.thenAcceptAsync(it ->{
            result[0]++;
        });

        completableFuture2.thenAcceptAsync(it ->{
            result[0]++;
        });

        completableFuture3.thenAcceptAsync(it ->{
            result[0]--;
        });

        completableFuture1.complete(commandDispatcherAsync);
        completableFuture2.complete(commandDispatcherAsync);
        completableFuture3.complete(commandDispatcherAsync);

    }
}