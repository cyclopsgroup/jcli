package org.cyclopsgroup.jcli;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

/**
 * A test to go over multi action use case
 */
public class MultiActionTest
{
    /**
     * Happy case of multi actions
     *
     * @throws Exception Allows any exception
     */
    @Test
    public void testMultiAction()
        throws Exception
    {
        Map<String, Class<? extends Object>> clis = new HashMap<String, Class<?>>();
        clis.put( "nocli", WithoutCli.class );
        clis.put( "simple", WithSimpleArgument.class );

        Class<? extends Object> type = clis.get( "nocli" );
        WithoutCli nocli = (WithoutCli) type.newInstance();
        ArgumentProcessor.newInstance( type ).process( Arrays.<String> asList( "-a", "aaaaa" ), nocli );
        assertEquals( "aaaaa", nocli.optionA );
    }
}
