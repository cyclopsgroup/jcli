package org.cyclopsgroup.jcli.impl;

import org.cyclopsgroup.jcli.annotation.Argument;

class AnnotationArgument
    implements org.cyclopsgroup.jcli.spi.Argument
{
    private Argument argument;

    /**
     * @param argument Argument annotation
     */
    AnnotationArgument( Argument argument )
    {
        this.argument = argument;
    }

    /**
     * @inheritDoc
     */
    @Override
    public String getDescription()
    {
        return argument.description();
    }

    /**
     * @inheritDoc
     */
    @Override
    public String getDisplayName()
    {
        return argument.displayName();
    }
}
