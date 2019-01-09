package org.cyclopsgroup.jcli;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import org.cyclopsgroup.jcli.spi.CommandLineParser;
import org.cyclopsgroup.jcli.spi.ParsingContext;

/**
 * The facade class that parses arguments and sets values to given bean
 *
 * @author <a href="mailto:jiaqi@cyclopsgroup.org">Jiaqi Guo</a>
 * @param <T> Type of bean it processes.
 */
public abstract class ArgumentProcessor<T> {
  /**
   * Create new instance with default parser, a {@link GnuParser}
   *
   * @param <T> Type of the bean
   * @param beanType Type of the bean
   * @return Instance of an implementation of argument processor
   */
  public static <T> ArgumentProcessor<T> forType(Class<? extends T> beanType) {
    return newInstance(beanType, new GnuParser());
  }

  @SuppressWarnings("unchecked")
  public static <T> ArgumentProcessor<T> forTypeOf(T bean) {
    return forType((Class<T>) bean.getClass());
  }

  /**
   * Create new instance with given bean type and command line parser that describes command line
   * sytnax.
   *
   * @param <T> type of bean
   * @param beanType Type of bean
   * @param parser command line parser that is aware of command line syntax
   * @return instance of an implementation of argument processor.
   */
  public static <T> ArgumentProcessor<T> newInstance(Class<? extends T> beanType,
      CommandLineParser parser) {
    return ArgumentProcessorFactory.getInstance().newProcessor(beanType, parser);
  }

  /**
   * @param beanType type of the bean.
   * @param <T> type of bean class.
   * @return instance of argument processor for given type of bean.
   * @deprecated Use {@link #forType(Class)} instead.
   */
  public static <T> ArgumentProcessor<T> newInstance(Class<T> beanType) {
    return forType(beanType);
  }

  /**
   * @return Implementation of parsing context
   */
  public abstract ParsingContext createParsingContext();

  /**
   * @param out Output to print help message to
   * @throws IOException Allows IO errors
   */
  public abstract void printHelp(PrintWriter out) throws IOException;

  /**
   * Process argument list and pass values to given bean
   *
   * @param arguments List of arguments
   * @param bean Bean to pass values to
   */
  public abstract T process(List<String> arguments, T bean);

  /**
   * Process argument array and pass values to given bean
   *
   * @param arguments Arary of arguments
   * @param bean Bean to pass values to
   */
  public T process(String[] arguments, T bean) {
    return process(Arrays.asList(arguments), bean);
  }

  /**
   * Verifies if given arguments meet requirement defined for processor.
   *
   * @param arguments array of command line arguments.
   * @return a result object.
   */
  public abstract ValidationResult validate(String[] arguments);
}
