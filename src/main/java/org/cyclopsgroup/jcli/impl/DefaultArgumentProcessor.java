package org.cyclopsgroup.jcli.impl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Properties;
import org.cyclopsgroup.jcli.ArgumentProcessor;
import org.cyclopsgroup.jcli.ValidationResult;
import org.cyclopsgroup.jcli.spi.CommandLineParser;
import org.cyclopsgroup.jcli.spi.Option;
import org.cyclopsgroup.jcli.spi.ParsingContext;
import com.google.common.collect.ImmutableMap;

class DefaultArgumentProcessor<T> extends ArgumentProcessor<T> {
  static final String ARGUMENT_REFERNCE_NAME = "----arguments----";

  private final CommandLineParser parser;
  private final Class<? extends T> beanType;

  DefaultArgumentProcessor(Class<? extends T> beanType, CommandLineParser parser) {
    this.parser = parser;
    this.beanType = beanType;
  }

  @Override
  public ParsingContext createParsingContext() {
    return createParsingContextInternal();
  }

  private AnnotationParsingContext<T> createParsingContextInternal() {
    return new ParsingContextBuilder<T>(beanType).build();
  }

  @Override
  public void printHelp(PrintWriter out) throws IOException {
    DefaultHelpPrinter.printHelp(createParsingContextInternal(), out);
  }

  @Override
  public T process(Properties props, T bean) {
    AnnotationParsingContext<T> context = createParsingContextInternal();
    ImmutableMap.Builder<String, Option> optionByName = ImmutableMap.builder();
    for (Option option : context.options()) {
      if (!option.getName().isEmpty()) {
        optionByName.put(option.getName(), option);
      }
      if (!option.getLongName().isEmpty()) {
        optionByName.put(option.getLongName(), option);
      }
    }
    return null;
  }

  @Override
  public T process(List<String> arguments, T bean) {
    DefaultBeanProcessor.process(createParsingContextInternal(), arguments, bean, parser);
    return bean;
  }

  @Override
  public ValidationResult validate(String[] arguments) {
    // TODO Auto-generated method stub
    return null;
  }
}
