package org.cyclopsgroup.jcli.impl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import org.cyclopsgroup.jcli.ArgumentProcessor;
import org.cyclopsgroup.jcli.ValidationResult;
import org.cyclopsgroup.jcli.spi.CommandLineParser;
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
        DefaultHelpPrinter.printHelp( context, out );
    }

    /**
     * @inheritDoc
     */
    @Override
    public void process( List<String> arguments, T bean )
    {
        DefaultBeanProcessor.process( context, arguments, bean, parser );
    }

    /**
     * @inheritDoc
     */
    @Override
    public ValidationResult validate( String[] arguments )
    {
        // TODO Auto-generated method stub
        return null;
    }
}