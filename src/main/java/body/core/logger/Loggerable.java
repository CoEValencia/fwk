package body.core.logger;

import org.apache.log4j.Logger;

/**
 * Created by vbolinch on 09/02/2017.
 * Trait
 * */
public interface Loggerable extends LoggerableParent {

    default void loggerTrace(Object str){
        get().trace(str);
    }

    default void loggerDebug(String str){
        get().debug(str);
    }

    default void loggerInfo(String str){
        get().info(str);
    }

    default void loggerError(String str){
        get().error(str);
    }
}
