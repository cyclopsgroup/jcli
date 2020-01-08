package org.cyclopsgroup.jcli;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

public class MultiActionTest {
  @Test
  public void testMultiAction() throws InstantiationException, IllegalAccessException {
    Map<String, Class<?>> clis = new HashMap<String, Class<?>>();
    clis.put("nocli", WithoutCli.class);
    clis.put("simple", WithSimpleArgument.class);

    WithoutCli nocli = (WithoutCli) clis.get("nocli").newInstance();
    ArgumentProcessor.forTypeOf(nocli).process(Arrays.asList("-a", "aaaaa"), nocli);
    assertEquals("aaaaa", nocli.optionA);
  }
}
