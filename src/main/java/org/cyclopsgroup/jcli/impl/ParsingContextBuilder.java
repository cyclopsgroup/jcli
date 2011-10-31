package org.cyclopsgroup.jcli.impl;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cyclopsgroup.caff.conversion.AnnotatedConverter;
import org.cyclopsgroup.caff.conversion.Converter;
import org.cyclopsgroup.caff.ref.ValueReference;
import org.cyclopsgroup.jcli.annotation.Argument;
import org.cyclopsgroup.jcli.annotation.Cli;
import org.cyclopsgroup.jcli.annotation.MultiValue;
import org.cyclopsgroup.jcli.annotation.Option;

/**
 * Internal builder to create instance of {@link AnnotationParsingContext}
 *
 * @author <a href="mailto:jiaqi@cyclopsgroup.org">Jiaqi Guo</a>
 * @param <T> Type of bean to parse
 */
class ParsingContextBuilder<T>
{
    @SuppressWarnings( "unchecked" )
    private static <T, P> Reference<T> createReference( Class<? extends T> beanType, PropertyDescriptor descriptor,
                                                        String longName )
    {
        Class<P> valueType = (Class<P>) descriptor.getPropertyType();
        MultiValue multiValue = getAnnotation( descriptor, MultiValue.class );
        if ( multiValue != null )
        {
            valueType = (Class<P>) multiValue.valueType();
        }

        Converter<P> converter = new AnnotatedConverter<P>( valueType, descriptor );
        ValueReference<T> reference = ValueReference.instanceOf( descriptor );
        if ( multiValue != null )
        {
            return new MultiValueReference<T>( beanType, converter, reference, longName, multiValue.listType() );
        }
        return new SingleValueReference<T>( beanType, converter, reference, longName );
    }

    /**
     * Get annotation from either writer or reader of a Java property
     *
     * @param <A> Type of annotation
     * @param descriptor Field descriptor from which annotation is searched
     * @param type Type of annotation
     * @return Annotation or null if it's not found
     */
    private static <A extends Annotation> A getAnnotation( PropertyDescriptor descriptor, Class<A> type )
    {
        A a = null;
        if ( descriptor.getWriteMethod() != null )
        {
            a = descriptor.getWriteMethod().getAnnotation( type );
        }
        if ( a == null && descriptor.getReadMethod() != null )
        {
            a = descriptor.getReadMethod().getAnnotation( type );
        }
        return a;
    }

    private final Class<? extends T> beanType;

    ParsingContextBuilder( Class<? extends T> beanType )
    {
        this.beanType = beanType;
    }

    AnnotationParsingContext<T> build()
    {
        List<AnnotationOption> options = new ArrayList<AnnotationOption>();
        Map<String, Reference<T>> references = new HashMap<String, Reference<T>>();
        Cli cliAnnotation = beanType.getAnnotation( Cli.class );
        BeanInfo beanInfo;
        try
        {
            beanInfo = Introspector.getBeanInfo( beanType );
        }
        catch ( IntrospectionException e )
        {
            throw new RuntimeException( "Bean " + beanType + " is not correctly defined", e );
        }
        Argument argument = null;
        for ( PropertyDescriptor descriptor : beanInfo.getPropertyDescriptors() )
        {
            Method writer = descriptor.getWriteMethod();
            if ( writer == null )
            {
                continue;
            }
            Option option = getAnnotation( descriptor, Option.class );
            if ( option != null )
            {
                boolean flag =
                    ( descriptor.getPropertyType() == Boolean.TYPE || descriptor.getPropertyType() == Boolean.class );
                boolean multiValue = getAnnotation( descriptor, MultiValue.class ) != null;
                options.add( new AnnotationOption( option, flag, multiValue ) );
                references.put( option.name(), createReference( beanType, descriptor, option.longName() ) );
                continue;
            }
            Argument arg = getAnnotation( descriptor, Argument.class );
            if ( arg != null )
            {
                argument = arg;
                references.put( DefaultArgumentProcessor.ARGUMENT_REFERNCE_NAME,
                                createReference( beanType, descriptor, DefaultArgumentProcessor.ARGUMENT_REFERNCE_NAME ) );
                continue;
            }
        }
        return new AnnotationParsingContext<T>( references, options, new AnnotationCli( cliAnnotation ),
                                                argument == null ? null : new AnnotationArgument( argument ) );
    }
}
