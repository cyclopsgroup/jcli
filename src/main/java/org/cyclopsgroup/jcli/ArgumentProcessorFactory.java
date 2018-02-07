package org.cyclopsgroup.jcli;

import java.util.Iterator;
import java.util.ServiceLoader;

import org.cyclopsgroup.jcli.spi.CommandLineParser;

/**
 * Factory class for {@link ArgumentProcessor}
 *
 * @author <a href="mailto:jiaqi@cyclopsgroup.org">Jiaqi Guo</a>
 */
public abstract class ArgumentProcessorFactory {
  /**
   * @return Instance of ArgumentProcessorFactory. The implementation is determined by
   *         {@link ServiceLoader}
   */
  static ArgumentProcessorFactory getInstance() {
    Iterator<ArgumentProcessorFactory> factories =
        ServiceLoader.load(ArgumentProcessorFactory.class).iterator();
    if (factories.hasNext()) {
      return factories.next();
    }
    throw new AssertionError("Can't find an implementation of "
        + ArgumentProcessorFactory.class.getName() + " from service loader");
  }

  /**
   * Create new instance of {@link ArgumentProcessor}. The implementation of factory needs to
   * implement this method to create customized argument processor
   *
   * @param <T> Type of bean to process
   * @param beanType Type of bean to process
   * @param parser Command line parser that is aware of command line syntax
   * @return Instance of argument processor implementation
   */
  protected abstract <T> ArgumentProcessor<T> newProcessor(Class<? extends T> beanType,
      CommandLineParser parser);
}
