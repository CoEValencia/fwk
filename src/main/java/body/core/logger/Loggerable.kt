package body.core.logger

/**
 * Created by vbolinch on 09/02/2017.
 * Trait
 */
interface Loggerable : LoggerableParent {

    fun loggerTrace(str: Any)  = get().trace(str)

    fun loggerDebug(str: Any)  = get().debug(str)

    fun loggerInfo(str: Any) = get().info(str)

    fun loggerError(str: Any) = get().error(str)

}


