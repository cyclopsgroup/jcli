package org.cyclopsgroup.jcli.spi;

/**
 * Meta data for an option
 *
 * @author <a href="mailto:jiaqi@cyclopsgroup.org">Jiaqi Guo</a>
 */
public interface Option
{
    /**
     * @return Implied value of option, NULL if not specified
     */
    String getDefaultValue();

    /**
     * @return String description of the option
     */
    String getDescription();

    /**
     * @return Display name of option value
     */
    String getDisplayName();

    /**
     * @return Long option name used in command line
     */
    String getLongName();

    /**
     * @return Short option name used in command line
     */
    String getName();

    /**
     * @return True if option doesn't take a value
     */
    boolean isFlag();

    /**
     * @return True if there can be more than one of this option
     */
    boolean isMultiValue();

    /**
     * @return True if option is required
     */
    boolean isRequired();
}
