package body.core.logger;

/**
 * Created by vbolinch on 09/02/2017.
 * Trait
 */
public interface Loggerable extends LoggerableParent {

    default void loggerTrace(Object str) { getLogger().trace(str); }

    default void loggerDebug(Object str) { getLogger().debug(str); }

    default void loggerInfo(Object str) {  getLogger().info(str); }

    default void loggerError(Object str) {  getLogger().error(str); }
}
