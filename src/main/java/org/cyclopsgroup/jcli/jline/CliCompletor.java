package org.cyclopsgroup.jcli.jline;

import java.beans.IntrospectionException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.cyclopsgroup.caff.token.TokenEvent;
import org.cyclopsgroup.caff.token.TokenEventHandler;
import org.cyclopsgroup.caff.token.ValueTokenizer;
import org.cyclopsgroup.jcli.ArgumentProcessor;
import org.cyclopsgroup.jcli.AutoCompletable;
import org.cyclopsgroup.jcli.spi.Option;
import org.cyclopsgroup.jcli.spi.ParsingContext;
import org.jline.reader.Candidate;
import org.jline.reader.Completer;
import org.jline.reader.LineReader;
import org.jline.reader.ParsedLine;

/**
 * JLine completor implemented with JCli
 *
 * @author <a href="mailto:jiaqi.guo@gmail.com">Jiaqi Guo</a>
 */
public class CliCompletor
    implements Completer
{
    private static List<String> filterList( List<String> list, String prefix )
    {
        if ( StringUtils.isEmpty( prefix ) || list == null )
        {
            return list;
        }
        List<String> results = new ArrayList<String>();
        for ( String item : list )
        {
            if ( item.startsWith( prefix ) )
            {
                results.add( item );
            }
        }
        return results;
    }

    private final ParsingContext context;

    private final AutoCompletable completable;

    private final ValueTokenizer tokenizer;

    /**
     * @param cliBean Entyped AutoCompletable implementation or an normal bean
     * @param tokenizer Tokenizer for argument parsing
     * @throws IntrospectionException
     */
    public CliCompletor( final Object cliBean, final ValueTokenizer tokenizer )
    {
        Validate.notNull( cliBean, "Cli bean can't be NULL" );
        Validate.notNull( tokenizer, "String tokenizer can't be NULL" );
        context =
            ArgumentProcessor.forType( cliBean.getClass() ).createParsingContext();
        if ( cliBean instanceof AutoCompletable )
        {
            this.completable = (AutoCompletable) cliBean;
        }
        else
        {
            this.completable = new AutoCompletable()
            {
                public List<String> suggestArgument( String partialArgument )
                {
                    return Collections.emptyList();
                }

                public List<String> suggestOption( String optionName,
                                                   String partialOption )
                {
                    return Collections.emptyList();
                }
            };
        }
        this.tokenizer = tokenizer;
    }

    /**
     * @inheritDoc
     */
    @SuppressWarnings( { "unchecked", "rawtypes" } )
    public void complete(LineReader reader, ParsedLine line, List<Candidate> suggestions)
    {
        String command = line.line();
        ArgumentsInspector inspector = new ArgumentsInspector( context );
        final AtomicBoolean terminated = new AtomicBoolean( true );
        final AtomicInteger lastWordStart = new AtomicInteger( 0 );
        if ( StringUtils.isNotEmpty( command ) )
        {
            final List<String> args = new ArrayList<String>();
            tokenizer.parse( command, new TokenEventHandler()
            {

                public void handleEvent( TokenEvent ev )
                {
                    args.add( ev.getToken() );
                    terminated.set( ev.isTerminated() );
                    lastWordStart.set( ev.getStart() );
                }
            } );
            for ( String arg : args )
            {
                inspector.consume( arg );
            }
            if ( terminated.get() )
            {
                inspector.end();
            }
        }
        // System.err.println( "command=[" + command + "], cursor=" + cursor +
        // ", state=" + inspector.getState().name()
        // + ", value=" + inspector.getCurrentValue() );
        List<String> candidates = new ArrayList<String>();
        switch ( inspector.getState() )
        {
            case READY:
                for ( Option o : inspector.getRemainingOptions() )
                {
                    candidates.add( "-" + o.getName() );
                }
                Collections.sort( candidates );
                candidates.addAll( suggestArguments( null ) );
                break;
            case OPTION:
            case LONG_OPTION:
                candidates.addAll( suggestOptionNames( inspector,
                                                       inspector.getCurrentValue() ) );
                break;
            case OPTION_VALUE:
                candidates.addAll( suggestOptionValue( inspector.getCurrentOption(),
                                                       inspector.getCurrentValue() ) );
                break;
            case ARGUMENT:
                candidates.addAll( suggestArguments( inspector.getCurrentValue() ) );
        }
        for ( String candidate : candidates )
        {
            suggestions.add( new Candidate( tokenizer.escape( candidate ) ) );
        }
    }

    private List<String> suggestArguments( String partialArgument )
    {
        List<String> results;
        if ( StringUtils.isEmpty( partialArgument ) )
        {
            results = completable.suggestArgument( null );
        }
        else
        {
            results = completable.suggestArgument( partialArgument );
            if ( results == null )
            {
                results =
                    filterList( completable.suggestArgument( null ),
                                partialArgument );
            }
        }
        if ( results == null )
        {
            results = Collections.emptyList();
        }
        else
        {
            results = new ArrayList<String>( results );
            Collections.sort( results );
        }
        return results;
    }

    private List<String> suggestOptionNames( ArgumentsInspector inspector,
                                             String value )
    {
        List<String> results = new ArrayList<String>();
        for ( Option o : inspector.getRemainingOptions() )
        {
            if ( value.startsWith( "--" ) && o.getLongName() != null
                && ( "--" + o.getLongName() ).startsWith( value ) )
            {
                results.add( "--" + o.getLongName() );
            }
            else if ( value.startsWith( "-" )
                && ( "-" + o.getName() ).startsWith( value ) )
            {
                results.add( "-" + o.getName() );
            }
        }
        Collections.sort( results );
        return results;
    }

    private List<String> suggestOptionValue( Option option, String partialValue )
    {
        List<String> results;
        if ( StringUtils.isEmpty( partialValue ) )
        {
            results = completable.suggestOption( option.getName(), null );
        }
        else
        {
            results =
                completable.suggestOption( option.getName(), partialValue );
            if ( results == null )
            {
                results =
                    filterList( completable.suggestOption( option.getName(),
                                                           null ), partialValue );
            }
        }
        if ( results == null )
        {
            results = Collections.emptyList();
        }
        else
        {
            results = new ArrayList<String>( results );
            Collections.sort( results );
        }
        return results;
    }
}
