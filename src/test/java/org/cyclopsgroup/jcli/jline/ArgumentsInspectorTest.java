package org.cyclopsgroup.jcli.jline;

import static org.junit.Assert.assertEquals;

import java.beans.IntrospectionException;

import org.cyclopsgroup.jcli.ArgumentProcessor;
import org.cyclopsgroup.jcli.Simple;
import org.junit.Before;
import org.junit.Test;

/**
 * Test case of {@link ArgumentsInspector}
 *
 * @author <a href="mailto:jiaqi.guo@gmail.com">Jiaqi Guo</a>
 */
public class ArgumentsInspectorTest
{
    private ArgumentsInspector ins;

    /**
     * Setup object to test
     *
     * @throws IntrospectionException
     */
    @Before
    public void setUp()
        throws IntrospectionException
    {
        ins = new ArgumentsInspector( ArgumentProcessor.forType( Simple.class ).createParsingContext() );
    }

    /**
     * Test state change of a sevies of argument consumption
     */
    @Test
    public void testConsume()
    {
        assertEquals( ArgumentsInspectorState.READY, ins.getState() );
        ins.consume( "xyz" );
        assertEquals( ArgumentsInspectorState.ARGUMENT, ins.getState() );
        assertEquals( "xyz", ins.getCurrentValue() );
        ins.consume( "-i" );
        assertEquals( ArgumentsInspectorState.OPTION, ins.getState() );
        ins.consume( "1234" );
        assertEquals( ArgumentsInspectorState.OPTION_VALUE, ins.getState() );
        assertEquals( "i", ins.getCurrentOption().getName() );
    }
}
