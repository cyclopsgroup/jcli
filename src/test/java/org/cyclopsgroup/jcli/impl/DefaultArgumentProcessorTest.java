package org.cyclopsgroup.jcli.impl;

import static com.google.common.truth.Truth.assertThat;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Properties;
import org.cyclopsgroup.jcli.ArgumentProcessor;
import org.cyclopsgroup.jcli.Simple;
import org.junit.Test;

public class DefaultArgumentProcessorTest {
  @Test
  public void testAvailability() {
    ArgumentProcessor<Simple> p = ArgumentProcessor.newInstance(Simple.class, null);
    assertThat(p).isNotNull();
    assertThat(p).isInstanceOf(DefaultArgumentProcessor.class);
  }

  @Test
  public void testPrintHelp() throws IOException {
    ArgumentProcessor<Simple> p = ArgumentProcessor.newInstance(Simple.class, null);
    p.printHelp(new PrintWriter(new OutputStreamWriter(System.out), true));
  }

  @Test
  public void testProcessProperties() {
    Properties props = new Properties();
    props.setProperty("field1", "abc");
    props.setProperty("field2", "xyz");
    Simple s = ArgumentProcessor.forType(Simple.class).process(props, new Simple());
    assertThat(s.getStringField1()).isEqualTo("abc");
    assertThat(s.getStringField2()).isEqualTo("xyz");
  }
}
