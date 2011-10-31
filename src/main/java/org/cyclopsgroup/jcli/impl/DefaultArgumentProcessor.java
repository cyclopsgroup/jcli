package org.cyclopsgroup.jcli.impl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.cyclopsgroup.caff.format.Format;
import org.cyclopsgroup.caff.format.Formats;
import org.cyclopsgroup.jcli.ArgumentProcessor;
import org.cyclopsgroup.jcli.spi.CommandLine;
import org.cyclopsgroup.jcli.spi.CommandLineParser;
import org.cyclopsgroup.jcli.spi.Option;
import org.cyclopsgroup.jcli.spi.ParsingContext;

class DefaultArgumentProcessor<T>
    extends ArgumentProcessor<T>
{
    static final String ARGUMENT_REFERNCE_NAME = "----arguments----";

    private final AnnotationParsingContext<T> context;

    private final CommandLineParser parser;

    DefaultArgumentProcessor( Class<? extends T> beanType, CommandLineParser parser )
    {
        this.parser = parser;
        context = new ParsingContextBuilder<T>( beanType ).build();
    }

    /**
     * @inheritDoc
     */
    @Override
    public ParsingContext createParsingContext()
    {
        return context;
    }

    AnnotationParsingContext<T> getContext()
    {
        return context;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void printHelp( PrintWriter out )
        throws IOException
    {
        out.println( "[USAGE]" );
        out.println( "  " + context.cli().getName() + ( context.options().isEmpty() ? "" : " <OPTIONS>" )
            + ( context.argument() == null ? "" : " <ARGS>" ) );
        if ( StringUtils.isNotBlank( context.cli().getDescription() ) )
        {
            out.println( "[DESCRIPTION]" );
            out.println( "  " + context.cli().getDescription() );
        }
        if ( !context.options().isEmpty() )
        {
            out.println( "[OPTIONS]" );
            Format<OptionHelp> helpFormat = Formats.newFixLengthFormat( OptionHelp.class );
            for ( Option option : context.options() )
            {
                String line = helpFormat.formatToString( new OptionHelp( option ) ).trim();
                out.println( "  " + line );
            }
        }
        if ( context.argument() != null )
        {
            out.println( "[ARGS]" );
            out.println( "  <" + context.argument().getDisplayName() + ">... " + context.argument().getDescription() );
        }
        if ( !StringUtils.isBlank( context.cli().getNote() ) )
        {
            out.println( "[NOTE]" );
            out.println( "  " + context.cli().getNote() );
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public void process( List<String> arguments, T bean )
    {
        CommandLine cli = parser.parse( arguments, context );
        Map<String, List<String>> multiValues = new HashMap<String, List<String>>();
        for ( CommandLine.OptionValue ov : cli.getOptionValues() )
        {
            Reference<T> ref = context.lookupReference( ov.name, !ov.shortName );
            if ( ref == null )
            {
                throw new AssertionError( "Option " + ov.name + " doesn't exist" );
            }
            if ( ref instanceof SingleValueReference<?> )
            {
                ( (SingleValueReference<T>) ref ).setValue( bean, ov.value );
                continue;
            }
            String optionName;
            if ( ov.shortName )
            {
                optionName = ov.name;
            }
            else
            {
                optionName = context.optionWithLongName( ov.name ).getName();
            }
            List<String> values = multiValues.get( optionName );
            if ( values == null )
            {
                values = new ArrayList<String>();
                multiValues.put( optionName, values );
            }
            values.add( ov.value );
        }
        for ( Map.Entry<String, List<String>> entry : multiValues.entrySet() )
        {
            MultiValueReference<T> ref = (MultiValueReference<T>) context.lookupReference( entry.getKey(), false );
            ref.setValues( bean, entry.getValue() );
        }
        Reference<T> ref = context.lookupReference( ARGUMENT_REFERNCE_NAME, false );
        if ( ref == null )
        {
            return;
        }
        if ( ref instanceof MultiValueReference<?> )
        {
            ( (MultiValueReference<T>) ref ).setValues( bean, cli.getArguments() );
        }
        else
        {
            String value = cli.getArguments().isEmpty() ? null : cli.getArguments().get( 0 );
            ( (SingleValueReference<T>) ref ).setValue( bean, value );
        }
    }
}