package org.cyclopsgroup.jcli;

import org.cyclopsgroup.jcli.annotation.Option;

public class BeanWithoutCli
{
    String optionA;

    String optionWithDash;

    @Option( name = "a" )
    public void setOptionA( String value )
    {
        this.optionA = value;
    }

    @Option( name = "d", longName = "with-dash" )
    public void setOptionWithDash( String value )
    {
        this.optionWithDash = value;
    }
}
