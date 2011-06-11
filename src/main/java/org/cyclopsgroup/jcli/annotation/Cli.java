package org.cyclopsgroup.jcli.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Type annotated with Cli is considered as a command where options and arguments are set
 * 
 * @author <a href="mailto:jiaqi.guo@gmail.com">Jiaqi Guo</a>
 */
@Documented
@Target( ElementType.TYPE )
@Retention( RetentionPolicy.RUNTIME )
public @interface Cli
{
    /**
     * @return String description of command
     */
    String description() default "";

    /**
     * @return Name of command
     */
    String name();

    /**
     * @return Note displayed as footer
     */
    String note() default "";

    /**
     * @return True if unexpected option or argument is expected to cause error
     */
    boolean restrict() default true;
}
