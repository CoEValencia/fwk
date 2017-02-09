package body.core.logger;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by vicboma on 09/02/17.
 */
public class LoggerableParentTest implements LoggerableParent {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void isIntanceInterface() throws Exception {
        String expected = "interface body.core.logger.LoggerableParent";
        String result = this.getClass().getInterfaces()[0].toString();
        Assert.assertEquals(expected,result);
    }

    @Test
    public void isLogger() throws Exception {
        final Class<Logger> _class = Logger.class;
        Assert.assertEquals(this.get().getClass(),_class);
    }
}