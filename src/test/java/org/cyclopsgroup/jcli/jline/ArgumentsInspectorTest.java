package org.cyclopsgroup.jcli.jline;

import static org.junit.Assert.assertEquals;

import java.beans.IntrospectionException;
import org.cyclopsgroup.jcli.ArgumentProcessor;
import org.cyclopsgroup.jcli.Simple;
import org.junit.Before;
import org.junit.Test;

public class ArgumentsInspectorTest {
  private ArgumentsInspector ins;

  @Before
  public void setUp() throws IntrospectionException {
    ins = new ArgumentsInspector(ArgumentProcessor.forType(Simple.class).createParsingContext());
  }

  @Test
  public void testConsume() {
    assertEquals(ArgumentsInspectorState.READY, ins.getState());
    ins.consume("xyz");
    assertEquals(ArgumentsInspectorState.ARGUMENT, ins.getState());
    assertEquals("xyz", ins.getCurrentValue());
    ins.consume("-i");
    assertEquals(ArgumentsInspectorState.OPTION, ins.getState());
    ins.consume("1234");
    assertEquals(ArgumentsInspectorState.OPTION_VALUE, ins.getState());
    assertEquals("i", ins.getCurrentOption().getName());
  }
}
