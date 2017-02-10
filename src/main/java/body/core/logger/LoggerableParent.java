package body.core.logger;

import org.apache.log4j.Logger;

/**
 * Created by vbolinch on 09/02/2017.
 * Trait
 */
public interface LoggerableParent {
    default Logger get() { return Logger.getLogger(this.getClass()); }
}
