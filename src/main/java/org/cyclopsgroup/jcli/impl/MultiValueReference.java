package org.cyclopsgroup.jcli.impl;

import java.util.Collection;
import java.util.List;

import org.cyclopsgroup.caff.conversion.Converter;
import org.cyclopsgroup.caff.ref.ValueReference;

class MultiValueReference<T>
    extends Reference<T>
{
    private final Class<?> listType;

    MultiValueReference( Class<T> beanType, Converter<?> converter, ValueReference<T> ref, String longName,
                         Class<?> listType )
    {
        super( beanType, converter, ref, longName );
        this.listType = listType;
    }

    /**
     * Write multi value to bean
     *
     * @param bean Bean to set values
     * @param values List of values to set
     */
    @SuppressWarnings( "unchecked" )
    <P> void setValues( T bean, List<String> values )
    {
        Collection<P> col;
        try
        {
            col = (Collection<P>) listType.newInstance();
        }
        catch ( InstantiationException e )
        {
            throw new RuntimeException( "Can't instantiate " + listType, e );
        }
        catch ( IllegalAccessException e )
        {
            throw new RuntimeException( "Can't access default constructor of " + listType );
        }
        for ( String value : values )
        {
            col.add( (P) converter.fromCharacters( value ) );
        }
        ref.writeValue( col, bean );
    }
}
