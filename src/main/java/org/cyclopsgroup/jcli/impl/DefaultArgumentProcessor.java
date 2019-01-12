package org.cyclopsgroup.jcli.impl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.cyclopsgroup.jcli.ArgumentProcessor;
import org.cyclopsgroup.jcli.ValidationResult;
import org.cyclopsgroup.jcli.spi.CommandLineParser;
import org.cyclopsgroup.jcli.spi.Option;
import org.cyclopsgroup.jcli.spi.ParsingContext;

class DefaultArgumentProcessor<T> extends ArgumentProcessor<T> {
  static final String ARGUMENT_REFERNCE_NAME = "----arguments----";

  private final Class<? extends T> beanType;
  private final CommandLineParser parser;

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
  public T process(List<String> arguments, T bean) {
    DefaultBeanProcessor.process(createParsingContextInternal(), arguments, bean, parser);
    return bean;
  }

  @Override
  public T processProperties(Properties props, T bean) {
    AnnotationParsingContext<T> context = createParsingContextInternal();
    Map<String, Option> optionByName = new HashMap<>();
    for (Option option : context.options()) {
      if (!option.getName().isEmpty()) {
        optionByName.put(option.getName(), option);
      }
      if (!option.getLongName().isEmpty()) {
        optionByName.put(option.getLongName(), option);
      }
    }
    for (Map.Entry<Object, Object> e : props.entrySet()) {
      String key = (String) e.getKey();
      Option option = optionByName.get(key);
      if (option == null) {
        continue;
      }
      String value = (String) e.getValue();
      Reference<T> ref = context.lookupReference(option.getLongName(), true);
      if (ref instanceof SingleValueReference) {
        ((SingleValueReference<T>) ref).setValue(bean, value);
      } else if (ref instanceof MultiValueReference) {
        ((MultiValueReference<T>) ref).setValues(bean, Arrays.asList(value));
      }
    }
    return bean;
  }

  @Override
  public ValidationResult validate(String[] arguments) {
    // TODO Auto-generated method stub
    return null;
  }
}
