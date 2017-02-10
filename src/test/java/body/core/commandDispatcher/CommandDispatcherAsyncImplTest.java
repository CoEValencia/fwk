package body.core.commandDispatcher;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
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
         final CompletableFuture<Boolean> completableFuture = CompletableFuture.supplyAsync(() -> {
             try {
                 Thread.sleep(2000);
             } catch (InterruptedException e) {
                 e.printStackTrace();
             }
             finally {
                 return Boolean.TRUE;
             }
         });

        completableFuture.thenAcceptAsync(it ->{
            Assert.assertTrue(it);
        });

        commandDispatcherAsync.dispatch(testCommand);
    }

    @Test
    public void testDispatch2() throws Exception {
        final CompletableFuture<Boolean> completableFuture = CompletableFuture.completedFuture(true);

        completableFuture.thenAcceptAsync(it ->{
            Assert.assertTrue(it);
        });

        commandDispatcherAsync.dispatch(testCommand);
    }

    @Test
    public void testDispatchCancel() throws Exception {
        final CompletableFuture<Boolean> completableFuture = CompletableFuture.completedFuture(false);

        completableFuture.thenAcceptAsync(it ->{
            Assert.assertTrue(it);
        });

        commandDispatcherAsync.dispatch(testCommand);
        completableFuture.cancel(true);
    }
}