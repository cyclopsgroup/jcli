package org.cyclopsgroup.jcli.impl;

import org.apache.commons.lang.StringUtils;
import org.cyclopsgroup.caff.format.FixLengthField;
import org.cyclopsgroup.caff.format.FixLengthType;
import org.cyclopsgroup.jcli.spi.Option;

/**
 * An internal POJO to help printing out usage page
 *
 * @author <a href="mailto:jiaqi@cyclopsgroup.org">Jiaqi Guo</a>
 */
@FixLengthType( length = 256 )
public class OptionHelp
{
    private final Option option;

    OptionHelp( Option option )
    {
        this.option = option;
    }

    /**
     * @return Description of option
     */
    @FixLengthField( start = 26, length = 228 )
    public String getDescription()
    {
        String desc = option.getDescription();
        if ( !option.isFlag() && StringUtils.isNotBlank( option.getDefaultValue() ) )
        {
            desc += "(Default value is " + option.getDefaultValue() + ")";
        }
        return desc;
    }

    /**
     * @return Name of option value
     */
    @FixLengthField( start = 15, length = 10 )
    public String getDisplayName()
    {
        return option.isFlag() ? null : "<" + option.getDisplayName() + ">";
    }

    /**
     * @return Long name of option
     */
    @FixLengthField( start = 4, length = 10 )
    public String getLongName()
    {
        return StringUtils.isBlank( option.getLongName() ) ? null : "--" + option.getLongName();
    }

    /**
     * @return Short name of option
     */
    @FixLengthField( start = 1, length = 2 )
    public String getName()
    {
        return "-" + option.getName();
    }
}
