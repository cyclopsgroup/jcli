package org.cyclopsgroup.jcli.impl;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.cyclopsgroup.caff.conversion.Converter;
import org.cyclopsgroup.caff.ref.ValueReference;

/**
 * A reference to allow to set value as an attribute of bean
 *
 * @author <a href="mailto:jiaqi@cyclopsgroup.org">Jiaqi Guo</a>
 * @param <T> Type of bean to operate on
 */
abstract class Reference<T>
{
    final Converter<?> converter;

    final String longName;

    final ValueReference<T> ref;

    Reference( Converter<?> converter, ValueReference<T> ref, String longName )
    {
        this.converter = converter;
        this.ref = ref;
        this.longName = longName;
    }

    /**
     * @inheritDoc
     */
    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString( this );
    }
}
