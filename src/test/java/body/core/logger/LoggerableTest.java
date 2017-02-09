package body.core.logger;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

/**
 * Created by vicboma on 09/02/17.
 */
public class LoggerableTest implements Loggerable {

    private LoggerableTest loggable;

    @Before
    public void setUp() throws Exception {
        loggable = getMock();
    }

    @After
    public void tearDown() throws Exception {
        loggable = null;
    }

    @Test
    public void isIntanceInterface() throws Exception {
        String expected = "interface body.core.logger.Loggerable";
        String result = this.getClass().getInterfaces()[0].toString();
        Assert.assertEquals(expected,result);
    }

    @Test
    public void isLoggerTrace() throws Exception {
        String strTrace = "trace";
        final LoggerableTest loggable = getMock();
        loggable.loggerDebug(strTrace);
        verify(loggable).loggerDebug(strTrace);
    }

    @Test
    public void isLoggerDebug() throws Exception {
        String strDebug = "debug";
        final Loggerable loggable = getMock();
        loggable.loggerDebug(strDebug);
        verify(loggable).loggerDebug(strDebug);
    }

    @Test
    public void isLoggerInfo() throws Exception {
        String strInfo = "info";
        final Loggerable loggable = getMock();
        loggable.loggerDebug(strInfo);
        verify(loggable).loggerDebug(strInfo);
    }

    @Test
    public void isLoggerError() throws Exception {
        String strError = "error";
        final Loggerable loggable = mock(this.getClass());
        loggable.loggerDebug(strError);
        verify(loggable).loggerDebug(strError);
    }

    private LoggerableTest getMock() {
        return mock(
                this.getClass(),
                withSettings()
                        .defaultAnswer(
                                invocation -> invocation.getMethod().isDefault()
                                        ? invocation.callRealMethod()
                                        : RETURNS_DEFAULTS.answer(invocation)
                        )
        );
    }
}