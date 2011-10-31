package org.cyclopsgroup.jcli;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

import org.cyclopsgroup.jcli.spi.CommandLineParser;
import org.cyclopsgroup.jcli.spi.ParsingContext;

/**
 * The facade class that parses arguments and sets values to given bean
 *
 * @author <a href="mailto:jiaqi@cyclopsgroup.org">Jiaqi Guo</a>
 * @param <T>
 */
public abstract class ArgumentProcessor<T>
{
    /**
     * Create new instance with default parser, a {@link GnuParser}
     *
     * @param <T> Type of the bean
     * @param beanType Type of the bean
     * @return Instance of an implementation of argument processor
     */
    public static <T> ArgumentProcessor<T> newInstance( Class<? extends T> beanType )
    {
        return newInstance( beanType, new GnuParser() );
    }

    /**
     * Create new instance with given bean type and command line parser that describes command line sytnax
     *
     * @param <T> Type of bean
     * @param beanType Type of bean
     * @param parser Command line parser that is aware of command line syntax
     * @return Instance of an implementation of argument processor
     */
    public static <T> ArgumentProcessor<T> newInstance( Class<? extends T> beanType, CommandLineParser parser )
    {
        return ArgumentProcessorFactory.getInstance().newProcessor( beanType, parser );
    }

    /**
     * @return Implementation of parsing context
     */
    public abstract ParsingContext createParsingContext();

    /**
     * @param out Output to print help message to
     * @throws IOException Allows IO errors
     */
    public abstract void printHelp( PrintWriter out )
        throws IOException;

    /**
     * Process argument list and pass values to given bean
     *
     * @param arguments List of arguments
     * @param bean Bean to pass values to
     */
    public abstract void process( List<String> arguments, T bean );

    /**
     * Process argument array and pass values to given bean
     *
     * @param arguments Arary of arguments
     * @param bean Bean to pass values to
     */
    public void process( String[] arguments, T bean )
    {
        process( Arrays.asList( arguments ), bean );
    }
}
