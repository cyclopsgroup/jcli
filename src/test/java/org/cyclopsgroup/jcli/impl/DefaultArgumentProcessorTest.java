package org.cyclopsgroup.jcli.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ServiceLoader;
import org.cyclopsgroup.jcli.ArgumentProcessor;
import org.cyclopsgroup.jcli.Simple;
import org.junit.Test;

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
   * @throws IOException
   */
  @Test
  public void testPrintHelp() throws IOException {
    ArgumentProcessor<Simple> p = ArgumentProcessor.newInstance(Simple.class, null);
    p.printHelp(new PrintWriter(new OutputStreamWriter(System.out), true));
  }
}
