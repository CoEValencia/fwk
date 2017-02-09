package body.core.logger

import org.apache.log4j.Logger

/**
 * Created by vbolinch on 09/02/2017.
 * Trait
 */
interface LoggerableParent {
    fun get() = Logger.getLogger(this.javaClass)
}
