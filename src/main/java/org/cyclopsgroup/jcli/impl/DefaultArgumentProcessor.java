package org.cyclopsgroup.jcli.impl;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.AccessControlContext;
import java.security.AccessController;
import java.util.List;
import org.cyclopsgroup.jcli.ArgumentProcessor;
import org.cyclopsgroup.jcli.ValidationResult;
import org.cyclopsgroup.jcli.spi.CommandLineParser;
import org.cyclopsgroup.jcli.spi.ParsingContext;

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
    return createParsingContextInternal(AccessController.getContext());
  }

  private AnnotationParsingContext<T> createParsingContextInternal(AccessControlContext access) {
    return new ParsingContextBuilder<T>(beanType).build();
  }

  @Override
  public void printHelp(PrintWriter out) throws IOException {
    DefaultHelpPrinter.printHelp(createParsingContextInternal(AccessController.getContext()), out);
  }

  @Override
  public T process(List<String> arguments, T bean, AccessControlContext context) {
    DefaultBeanProcessor.process(createParsingContextInternal(context), arguments, bean, parser);
    return bean;
  }

  @Override
  public ValidationResult validate(String[] arguments) {
    // TODO Auto-generated method stub
    return null;
  }
}
