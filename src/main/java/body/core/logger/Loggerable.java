package body.core.logger;

/**
 * Created by vbolinch on 09/02/2017.
 * Trait
 */
public interface Loggerable extends LoggerableParent {

    default void loggerTrace(Object str) { get().trace(str); }

    default void loggerDebug(Object str) { get().debug(str); }

    default void loggerInfo(Object str) {  get().info(str); }

    default void loggerError(Object str) {  get().error(str); }
}
