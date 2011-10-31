package org.cyclopsgroup.jcli.jline;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.beans.IntrospectionException;
import java.util.ArrayList;
import java.util.List;

import org.cyclopsgroup.caff.token.QuotedValueTokenizer;
import org.cyclopsgroup.jcli.Simple;
import org.junit.Before;
import org.junit.Test;

/**
 * Test case for {@link CliCompletor}
 *
 * @author <a href="mailto:jiaqi.guo@gmail.com">Jiaqi Guo</a>
 */
public class CliCompletorTest
{
    private List<String> candidates;

    private CliCompletor cc;

    /**
     * Set up completor to test
     *
     * @throws IntrospectionException
     */
    @Before
    public void setUp()
        throws IntrospectionException
    {
        cc = new CliCompletor( new Simple(), new QuotedValueTokenizer() );
        candidates = new ArrayList<String>();
    }

    /**
     * @throws IntrospectionException
     */
    @Test
    public void testCompleteWithEmpty()
        throws IntrospectionException
    {
        assertEquals( 0, cc.complete( "", 0, candidates ) );
        assertArrayEquals( new String[] { "-2", "-b", "-f", "-i", "11111", "22222", "33333" }, candidates.toArray() );
    }

    /**
     * Test with input <code>--</code>
     */
    @Test
    public void testCompleteWithLongOptionPrefix()
    {
        assertEquals( 0, cc.complete( "--", 2, candidates ) );
        assertArrayEquals( new String[] { "--boolean", "--field1", "--field2", "--tint" }, candidates.toArray() );
    }

    /**
     * Test with input <code>-f </code>
     */
    @Test
    public void testCompleteWithOption()
    {
        assertEquals( 3, cc.complete( "-f ", 3, candidates ) );
        assertArrayEquals( new String[] { "aaaa", "bbbb", "cccc" }, candidates.toArray() );
    }

    /**
     * Test with input <code>-</code>
     */
    @Test
    public void testCompleteWithOptionPrefix()
    {
        assertEquals( 0, cc.complete( "-", 1, candidates ) );
        assertArrayEquals( new String[] { "-2", "-b", "-f", "-i" }, candidates.toArray() );
    }

    /**
     * Test with input <code>--f</code>
     */
    @Test
    public void testCompleteWithPartialLongOption()
    {
        assertEquals( 0, cc.complete( "--f", 3, candidates ) );
        assertArrayEquals( new String[] { "--field1", "--field2" }, candidates.toArray() );
    }

    /**
     * Test with <code>-i 1 -f 11</code>
     */
    @Test
    public void testCompleteWithSecondOption()
    {
        assertEquals( 8, cc.complete( "-i 1 -f aa", 11, candidates ) );
        assertArrayEquals( new String[] { "aaaa" }, candidates.toArray() );
    }
}
