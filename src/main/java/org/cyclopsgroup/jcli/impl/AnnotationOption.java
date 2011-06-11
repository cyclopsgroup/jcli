package org.cyclopsgroup.jcli.impl;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.cyclopsgroup.jcli.annotation.Option;

class AnnotationOption
    implements org.cyclopsgroup.jcli.spi.Option
{
    private final boolean flag;

    private final boolean multiValue;

    private final Option option;

    AnnotationOption( Option option, boolean flag, boolean multiValue )
    {
        this.option = option;
        this.flag = flag;
        this.multiValue = multiValue;
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean equals( Object obj )
    {
        return EqualsBuilder.reflectionEquals( this, obj );
    }

    /**
     * @inheritDoc
     */
    @Override
    public String getDefaultValue()
    {
        return option.defaultValue();
    }

    /**
     * @inheritDoc
     */
    @Override
    public String getDescription()
    {
        return option.description();
    }

    /**
     * @inheritDoc
     */
    @Override
    public String getDisplayName()
    {
        return option.displayName();
    }

    /**
     * @inheritDoc
     */
    @Override
    public String getLongName()
    {
        return option.longName();
    }

    /**
     * @inheritDoc
     */
    @Override
    public String getName()
    {
        return option.name();
    }

    /**
     * @inheritDoc
     */
    @Override
    public int hashCode()
    {
        return HashCodeBuilder.reflectionHashCode( this );
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean isFlag()
    {
        return flag;
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean isMultiValue()
    {
        return multiValue;
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean isRequired()
    {
        return option.required();
    }

}
