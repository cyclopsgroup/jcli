package org.cyclopsgroup.jcli;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import org.junit.Test;

/**
 * Verify the correctness of GNU argument parser
 *
 * @author <a href="mailto:jiaqi@cyclopsgroup.org">Jiaqi Guo</a>
 */
public class GnuParserTest {
  /** Verify use cases with all possible types of arguments */
  @Test
  public void testCombination() {
    ArgumentProcessor<Simple> p = ArgumentProcessor.newInstance(Simple.class, new GnuParser());
    Simple b = new Simple();
    p.process(new String[] {"-i", "123", "a", "-f", "abc", "b"}, b);

    assertEquals(123, b.getIntField());
    assertEquals("abc", b.getStringField1());
    assertEquals(Arrays.asList("a", "b"), b.getValues());
    assertFalse(b.isBooleanField());
  }

  /** Verify that long option name with dash is acceptable */
  @Test
  public void testLongOptionWithDash() {
    ArgumentProcessor<WithoutCli> p =
        ArgumentProcessor.newInstance(WithoutCli.class, new GnuParser());
    WithoutCli b = new WithoutCli();
    p.process(new String[] {"--with-dash", "x"}, b);
    assertEquals("x", b.optionWithDash);
  }

  /** Verify empty arguments doesn't reset default arguments */
  @Test
  public void testNormalBeanWithoutValues() {
    ArgumentProcessor<Simple> p = ArgumentProcessor.newInstance(Simple.class, new GnuParser());
    Simple b = new Simple();
    p.process(new String[] {"-i", "123", "--field1", "abc"}, b);

    assertEquals(123, b.getIntField());
    assertEquals("abc", b.getStringField1());
    assertEquals(new ArrayList<String>(), b.getValues());
    assertFalse(b.isBooleanField());
  }

  /** Verify simple argument can be handled correctly */
  @Test
  public void testSimpleArgumentWithMultiValues() {
    ArgumentProcessor<WithSimpleArgument> p =
        ArgumentProcessor.newInstance(WithSimpleArgument.class, new GnuParser());
    WithSimpleArgument b = new WithSimpleArgument();
    p.process(new String[] {"a", "b"}, b);
    assertEquals("a", b.getArg());
  }

  /** Verify simple argument without value can be handled correctly */
  @Test
  public void testSimpleArgumentWithoutValue() {
    ArgumentProcessor<WithSimpleArgument> p =
        ArgumentProcessor.newInstance(WithSimpleArgument.class, new GnuParser());
    WithSimpleArgument b = new WithSimpleArgument();
    p.process(new String[] {}, b);
    assertNull(b.getArg());
  }

  @Test
  public void testWithDash() {
    ArgumentProcessor<Simple> p = ArgumentProcessor.forType(Simple.class);
    Simple s = new Simple();
    p.process(new String[] {"-f", "-1"}, s);
    assertEquals("-1", s.getStringField1());
  }

  /** Verify boolean is handled correctly */
  @Test
  public void testWithFlag() {
    ArgumentProcessor<Simple> p = ArgumentProcessor.newInstance(Simple.class, new GnuParser());
    Simple b = new Simple();
    p.process(new String[] {"-b", "123"}, b);
    assertTrue(b.isBooleanField());
  }

  /** Verify multi value option is handled correctly */
  @Test
  public void testWithMultiValueOption() {
    ArgumentProcessor<WithMultiValueOption> p =
        ArgumentProcessor.newInstance(WithMultiValueOption.class, new GnuParser());
    WithMultiValueOption b = new WithMultiValueOption();
    p.process(new String[] {"a", "-o", "b", "-o", "c", "d", "--option", "e"}, b);
    assertEquals(Arrays.asList("b", "c", "e"), b.getOptions());
  }

  /** Verify negative numbers are accepted */
  @Test
  public void testWithNegativeNumbers() {
    ArgumentProcessor<Simple> p = ArgumentProcessor.newInstance(Simple.class, new GnuParser());
    Simple b = new Simple();
    p.process(new String[] {"-1", "--1", "-2", "-3", "-4"}, b);
    assertEquals("-3", b.getStringField2());
    assertEquals(Arrays.asList("-1", "--1", "-4"), b.getValues());
  }

  /** Verify bean without Cli annotation is acceptable */
  @Test
  public void testWithoutCli() {
    ArgumentProcessor<WithoutCli> p =
        ArgumentProcessor.newInstance(WithoutCli.class, new GnuParser());
    WithoutCli b = new WithoutCli();
    p.process(new String[] {"-a", "aaaaa"}, b);
    assertEquals("aaaaa", b.optionA);
  }
}
