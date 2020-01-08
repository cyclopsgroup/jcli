package org.cyclopsgroup.jcli.spi;

import java.util.Collection;

/**
 * A builder class to create {@link CommandLine}
 *
 * @author <a href="mailto:jiaqi@cyclopsgroup.org">Jiaqi Guo</a>
 */
public final class CommandLineBuilder {
  private static final String FLAG_VALUE = Boolean.TRUE.toString();

  private final CommandLine cl = new CommandLine();

  /** @return The command line instance with information that has been added so far */
  public CommandLine toCommandLine() {
    return cl;
  }

  /**
   * @param argument Add an argument expression
   * @return Builder itself
   */
  public CommandLineBuilder withArgument(String argument) {
    cl.addArgument(argument);
    return this;
  }

  /**
   * @param arguments Add a list of arguments
   * @return Builder itself
   */
  public CommandLineBuilder withArguments(Collection<String> arguments) {
    for (String arg : arguments) {
      withArgument(arg);
    }
    return this;
  }

  /**
   * Add a flag option with its long name
   *
   * @param name Long name of the flag option to add
   * @return Builder itself
   */
  public CommandLineBuilder withLongFlag(String name) {
    withLongOption(name, FLAG_VALUE);
    return this;
  }

  /**
   * Add an option with its long name
   *
   * @param name Long name of the option to add
   * @param value Value of the option to add
   * @return Builder itself
   */
  public CommandLineBuilder withLongOption(String name, String value) {
    cl.addOptionValue(name, value, false);
    return this;
  }

  /**
   * Add a flag option with its short name
   *
   * @param name Short name of option to add
   * @return Builder itself
   */
  public CommandLineBuilder withShortFlag(String name) {
    withShortOption(name, FLAG_VALUE);
    return this;
  }

  /**
   * Add an option with its short name
   *
   * @param name Short name of the option to add
   * @param value Value of option to add
   * @return Builder itself
   */
  public CommandLineBuilder withShortOption(String name, String value) {
    cl.addOptionValue(name, value, true);
    return this;
  }
}
