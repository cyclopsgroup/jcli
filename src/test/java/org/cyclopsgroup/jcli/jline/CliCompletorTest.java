package org.cyclopsgroup.jcli.jline;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.beans.IntrospectionException;
import java.util.ArrayList;
import java.util.List;

import org.cyclopsgroup.caff.token.QuotedValueTokenizer;
import org.cyclopsgroup.jcli.Simple;
import org.jline.reader.Candidate;
import org.jline.reader.LineReader;
import org.jline.reader.ParsedLine;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;

/**
 * Test case for {@link CliCompletor}
 *
 * @author <a href="mailto:jiaqi.guo@gmail.com">Jiaqi Guo</a>
 */
public class CliCompletorTest {
  private List<Candidate> candidates;

  private CliCompletor cc;

  private Mockery context;

  private LineReader lineReader;

  private ParsedLine parsedLine;

  /**
   * Set up completor to test
   *
   * @throws IntrospectionException
   */
  @Before
  public void setUp() throws IntrospectionException {
    cc = new CliCompletor(new Simple(), new QuotedValueTokenizer());
    candidates = new ArrayList<Candidate>();
    context = new Mockery();
    lineReader = context.mock(LineReader.class);
    parsedLine = context.mock(ParsedLine.class);
  }

  /**
   * @throws IntrospectionException
   */
  @Test
  public void testCompleteWithEmpty() throws IntrospectionException {
    context.checking(new Expectations() {
      {
        oneOf(parsedLine).line();
        will(returnValue(""));
      }
    });
    cc.complete(lineReader, parsedLine, candidates);
    String[] expecteds = {"-2", "-b", "-f", "-i", "11111", "22222", "33333"};
    for (int i = 0; i < expecteds.length; i++) {
      assertThat(candidates.get(i).value(), is(expecteds[i]));
    }
  }

  /**
   * Test with input <code>--</code>
   */
  @Test
  public void testCompleteWithLongOptionPrefix() {
    context.checking(new Expectations() {
      {
        oneOf(parsedLine).line();
        will(returnValue("--"));
      }
    });
    cc.complete(lineReader, parsedLine, candidates);
    String[] expecteds = {"--boolean", "--field1", "--field2", "--tint"};
    for (int i = 0; i < expecteds.length; i++) {
      assertThat(candidates.get(i).value(), is(expecteds[i]));
    }
  }

  /**
   * Test with input <code>-f </code>
   */
  @Test
  public void testCompleteWithOption() {
    context.checking(new Expectations() {
      {
        oneOf(parsedLine).line();
        will(returnValue("-f "));
      }
    });
    cc.complete(lineReader, parsedLine, candidates);
    String[] expecteds = {"aaaa", "bbbb", "cccc"};
    for (int i = 0; i < expecteds.length; i++) {
      assertThat(candidates.get(i).value(), is(expecteds[i]));
    }
  }

  /**
   * Test with input <code>-</code>
   */
  @Test
  public void testCompleteWithOptionPrefix() {
    context.checking(new Expectations() {
      {
        oneOf(parsedLine).line();
        will(returnValue("-"));
      }
    });
    cc.complete(lineReader, parsedLine, candidates);
    String[] expecteds = {"-2", "-b", "-f", "-i"};
    for (int i = 0; i < expecteds.length; i++) {
      assertThat(candidates.get(i).value(), is(expecteds[i]));
    }
  }

  /**
   * Test with input <code>--f</code>
   */
  @Test
  public void testCompleteWithPartialLongOption() {
    context.checking(new Expectations() {
      {
        oneOf(parsedLine).line();
        will(returnValue("--f"));
      }
    });
    cc.complete(lineReader, parsedLine, candidates);
    String[] expecteds = {"--field1", "--field2"};
    for (int i = 0; i < expecteds.length; i++) {
      assertThat(candidates.get(i).value(), is(expecteds[i]));
    }
  }

  /**
   * Test with <code>-i 1 -f 11</code>
   */
  @Test
  public void testCompleteWithSecondOption() {
    context.checking(new Expectations() {
      {
        oneOf(parsedLine).line();
        will(returnValue("-i 1 -f aa"));
      }
    });
    cc.complete(lineReader, parsedLine, candidates);
    assertThat(candidates.get(0).value(), is("aaaa"));
  }
}
