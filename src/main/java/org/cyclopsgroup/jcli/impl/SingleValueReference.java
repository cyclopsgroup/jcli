package org.cyclopsgroup.jcli.impl;

import org.cyclopsgroup.caff.conversion.Converter;
import org.cyclopsgroup.caff.ref.ValueReference;

class SingleValueReference<T>
    extends Reference<T>
{
    SingleValueReference( Class<? extends T> beanType, Converter<?> converter, ValueReference<T> ref, String longName )
    {
        super( converter, ref, longName );
    }

    /**
     * Set a string value to bean based on known conversion rule and value reference
     *
     * @param bean Bean to set value to
     * @param value String expression of value to set
     */
    void setValue( T bean, String value )
    {
        ref.writeValue( converter.fromCharacters( value ), bean );
    }
}
