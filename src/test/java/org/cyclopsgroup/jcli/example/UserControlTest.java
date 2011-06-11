package org.cyclopsgroup.jcli.example;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.cyclopsgroup.jcli.ArgumentProcessor;
import org.cyclopsgroup.jcli.GnuParser;
import org.junit.Test;

/**
 * A test using {@link UserControl} as an example bean
 *
 * @author <a href="mailto:jiaqi@cyclopsgroup.org">Jiaqi Guo</a>
 */
public class UserControlTest
{
    /**
     * Verify user control parsing is correct
     */
    @Test
    public void testWithSingleUser()
    {
        ArgumentProcessor<UserControl> p = ArgumentProcessor.newInstance( UserControl.class, new GnuParser() );
        UserControl c = new UserControl();
        p.process( Arrays.asList( "-a", "ADD", "john" ), c );
        assertEquals( UserControlAction.ADD, c.getAction() );
        assertEquals( Arrays.asList( "john" ), c.getUserNames() );
    }
}
