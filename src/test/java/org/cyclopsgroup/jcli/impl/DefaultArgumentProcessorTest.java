package org.cyclopsgroup.jcli.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ServiceLoader;

import org.cyclopsgroup.jcli.ArgumentProcessor;
import org.cyclopsgroup.jcli.Simple;
import org.cyclopsgroup.jcli.spi.Option;
import org.junit.Test;

/**
 * Test of {@link DefaultArgumentProcessor}
 *
 * @author <a href="mailto:jiaqi@cyclopsgroup.org">Jiaqi Guo</a>
 */
public class DefaultArgumentProcessorTest {
  /**
   * Verify {@link ServiceLoader} can find right implementation
   */
  @Test
  public void testAvailability() {
    ArgumentProcessor<Simple> p = ArgumentProcessor.newInstance(Simple.class, null);
    assertNotNull(p);
    assertTrue(p.getClass() == DefaultArgumentProcessor.class);
  }

  /**
   * Verify parsing context is correctly created
   */
  @Test
  public void testParsingContextWithNormalBean() {
    DefaultArgumentProcessor<Simple> p = new DefaultArgumentProcessor<Simple>(Simple.class, null);
    Option a = p.getContext().optionWithShortName("i");
    assertEquals("i", a.getName());
    assertEquals("tint", a.getLongName());
    assertFalse(a.isFlag());

    Option b = p.getContext().optionWithLongName("field1");
    assertEquals("f", b.getName());
    assertEquals("field1", b.getLongName());
    assertEquals("field1", b.getLongName());
  }

  /**
   * @throws IOException
   */
  @Test
  public void testPrintHelp() throws IOException {
    ArgumentProcessor<Simple> p = ArgumentProcessor.newInstance(Simple.class, null);
    p.printHelp(new PrintWriter(new OutputStreamWriter(System.out), true));
  }
}
