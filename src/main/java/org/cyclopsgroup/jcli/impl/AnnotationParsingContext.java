package org.cyclopsgroup.jcli.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.cyclopsgroup.jcli.spi.Argument;
import org.cyclopsgroup.jcli.spi.Cli;
import org.cyclopsgroup.jcli.spi.Option;
import org.cyclopsgroup.jcli.spi.ParsingContext;

class AnnotationParsingContext<T> implements ParsingContext {
  private final AnnotationArgument argument;

  private final AnnotationCli cli;

  private final List<AnnotationOption> options;

  private final Map<String, Reference<T>> referenceMap;

  /**
   * @param referenceMap Map of references
   * @param options List options
   * @param cli Command line model
   * @param argument Argument definition
   */
  AnnotationParsingContext(Map<String, Reference<T>> referenceMap, List<AnnotationOption> options,
      AnnotationCli cli, AnnotationArgument argument) {
    this.options = options;
    this.referenceMap = referenceMap;
    this.cli = cli;
    this.argument = argument;
  }

  @Override
  public Argument argument() {
    return argument;
  }

  @Override
  public Cli cli() {
    return cli;
  }

  /**
   * Find reference with given name of option or argument
   *
   * @param name Name of option or argument
   * @param isLongName True if name is a long name
   * @return Reference that matches name or NULL
   */
  Reference<T> lookupReference(String name, boolean isLongName) {
    if (isLongName) {
      for (Reference<T> r : referenceMap.values()) {
        if (r.longName.equals(name)) {
          return r;
        }
      }
      return null;
    }
    return referenceMap.get(name);
  }

  @Override
  public List<Option> options() {
    return Arrays.asList(options.toArray(new Option[0]));
  }

  @Override
  public Option optionWithLongName(String longName) {
    for (Option o : options) {
      if (o.getLongName().equals(longName)) {
        return o;
      }
    }
    return null;
  }

  @Override
  public Option optionWithShortName(String shortName) {
    for (Option o : options) {
      if (o.getName().equals(shortName)) {
        return o;
      }
    }
    return null;
  }
}
