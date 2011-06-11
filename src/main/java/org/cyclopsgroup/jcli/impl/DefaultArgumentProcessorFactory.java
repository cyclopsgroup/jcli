package org.cyclopsgroup.jcli.impl;

import org.cyclopsgroup.jcli.ArgumentProcessor;
import org.cyclopsgroup.jcli.ArgumentProcessorFactory;
import org.cyclopsgroup.jcli.spi.CommandLineParser;

/**
 * Default implementation of {@link ArgumentProcessorFactory}
 *
 * @author <a href="mailto:jiaqi@cyclopsgroup.org">Jiaqi Guo</a>
 * @see {@link DefaultArgumentProcessor}
 */
public class DefaultArgumentProcessorFactory
    extends ArgumentProcessorFactory
{
    /**
     * @inheritDoc
     */
    @Override
    protected <T> ArgumentProcessor<T> newProcessor( Class<T> beanType, CommandLineParser parser )
    {
        try
        {
            return new DefaultArgumentProcessor<T>( beanType, parser );
        }
        catch ( RuntimeException e )
        {
            throw new RuntimeException( "Can't create argument processor for type " + beanType + " with parser "
                + parser, e );
        }
    }
}
