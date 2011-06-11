package org.cyclopsgroup.jcli.jline;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.cyclopsgroup.jcli.spi.Option;
import org.cyclopsgroup.jcli.spi.ParsingContext;

/**
 * Class that consumes arguments
 *
 * @author <a href="mailto:jiaqi.guo@gmail.com">Jiaqi Guo</a>
 */
class ArgumentsInspector
{
    private final ParsingContext context;

    private Option currentOption;

    private String currentValue;

    private final Set<Option> remainingOptions;

    private ArgumentsInspectorState state = ArgumentsInspectorState.READY;

    /**
     * @param context Parsing context
     */
    ArgumentsInspector( ParsingContext context )
    {
        this.context = context;
        remainingOptions = new HashSet<Option>( context.options() );
    }

    /**
     * @param argument Argument to consume
     */
    void consume( String argument )
    {
        if ( argument.startsWith( "--" ) )
        {
            state = ArgumentsInspectorState.LONG_OPTION;
        }
        else if ( argument.startsWith( "-" ) )
        {
            state = ArgumentsInspectorState.OPTION;
        }
        else
        {
            switch ( state )
            {
                case READY:
                    state = ArgumentsInspectorState.ARGUMENT;
                    break;
                case OPTION:
                    currentOption = context.optionWithShortName( currentValue.substring( 1 ) );
                case LONG_OPTION:
                    if ( state == ArgumentsInspectorState.LONG_OPTION )
                    {
                        currentOption = context.optionWithLongName( currentValue.substring( 2 ) );
                    }
                    if ( currentOption != null && !currentOption.isMultiValue() )
                    {
                        remainingOptions.remove( currentOption );
                    }
                    if ( currentOption == null || currentOption.isFlag() )
                    {
                        state = ArgumentsInspectorState.ARGUMENT;
                    }
                    else
                    {
                        state = ArgumentsInspectorState.OPTION_VALUE;
                    }
                    break;
                case OPTION_VALUE:
                case ARGUMENT:
                    state = ArgumentsInspectorState.ARGUMENT;
                    break;
                default:
                    throw new IllegalStateException();
            }
        }
        currentValue = argument;
    }

    /**
     * End the process
     */
    void end()
    {
        switch ( state )
        {
            case OPTION:
                currentOption = context.optionWithShortName( currentValue.substring( 1 ) );
            case LONG_OPTION:
                if ( state == ArgumentsInspectorState.LONG_OPTION )
                {
                    currentOption = context.optionWithLongName( currentValue.substring( 2 ) );
                }
                if ( currentOption != null && !currentOption.isMultiValue() )
                {
                    remainingOptions.remove( currentOption );
                }
                if ( currentOption == null || currentOption.isFlag() )
                {
                    state = ArgumentsInspectorState.ARGUMENT;
                }
                else
                {
                    state = ArgumentsInspectorState.OPTION_VALUE;
                }
                break;
            default:
                state = ArgumentsInspectorState.READY;
        }
        currentValue = null;
    }

    /**
     * @return The option being processed currently
     */
    Option getCurrentOption()
    {
        return currentOption;
    }

    /**
     * @return Current value
     */
    String getCurrentValue()
    {
        return currentValue;
    }

    /**
     * @return Set of remaining options
     */
    Set<Option> getRemainingOptions()
    {
        return Collections.unmodifiableSet( remainingOptions );
    }

    /**
     * @return Current parsing state
     */
    ArgumentsInspectorState getState()
    {
        return state;
    }
}
