package org.cyclopsgroup.jcli;

import org.cyclopsgroup.jcli.annotation.Argument;
import org.cyclopsgroup.jcli.annotation.Cli;

/**
 * A testing bean with one argument
 * 
 * @author <a href="mailto:jiaqi.guo@gmail.com">Jiaqi Guo</a>
 */
@Cli( name = "sample2" )
public class WithSimpleArgument
{
    private String arg;

    /**
     * @param arg Argument value
     */
    @Argument
    public final void setArg( String arg )
    {
        this.arg = arg;
    }

    /**
     * @return Argument value
     */
    public final String getArg()
    {
        return arg;
    }
}
