package org.cyclopsgroup.jcli.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Option of command
 *
 * @author <a href="mailto:jiaqi.guo@gmail.com">Jiaqi Guo</a>
 */
@Documented
@Target( { ElementType.METHOD, ElementType.FIELD } )
@Retention( RetentionPolicy.RUNTIME )
public @interface Option
{
    /**
     * @return Default value of option
     */
    String defaultValue() default "";

    /**
     * @return String description of option which is displayed in usage
     */
    String description() default "";

    /**
     * @return Display name of option
     */
    String displayName() default "value";

    /**
     * @return Long option name specified with double dash
     */
    String longName() default "";

    /**
     * @return Short option name specified with single dash
     */
    String name();

    /**
     * @return True if option has to be specified explicitly
     */
    boolean required() default false;
    
    /**
     * @return Array of conflicting options
     */
    String[] conflicts() default { };
}
