package org.cyclopsgroup.jcli.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.cyclopsgroup.jcli.spi.CommandLine;
import org.cyclopsgroup.jcli.spi.CommandLineParser;

class DefaultBeanProcessor {
  static <T> void process(AnnotationParsingContext<T> context, List<String> arguments, T bean,
      CommandLineParser parser) {
    CommandLine cli = parser.parse(arguments, context);
    Map<String, List<String>> multiValues = new HashMap<String, List<String>>();
    for (CommandLine.OptionValue ov : cli.getOptionValues()) {
      Reference<T> ref = context.lookupReference(ov.name, !ov.shortName);
      if (ref == null) {
        throw new AssertionError("Option " + ov.name + " doesn't exist");
      }
      if (ref instanceof SingleValueReference<?>) {
        ((SingleValueReference<T>) ref).setValue(bean, ov.value);
        continue;
      }
      String optionName;
      if (ov.shortName) {
        optionName = ov.name;
      } else {
        optionName = context.optionWithLongName(ov.name).getName();
      }
      List<String> values = multiValues.get(optionName);
      if (values == null) {
        values = new ArrayList<String>();
        multiValues.put(optionName, values);
      }
      values.add(ov.value);
    }
    for (Map.Entry<String, List<String>> entry : multiValues.entrySet()) {
      MultiValueReference<T> ref =
          (MultiValueReference<T>) context.lookupReference(entry.getKey(), false);
      ref.setValues(bean, entry.getValue());
    }
    Reference<T> ref =
        context.lookupReference(DefaultArgumentProcessor.ARGUMENT_REFERNCE_NAME, false);
    if (ref == null) {
      return;
    }
    if (ref instanceof MultiValueReference<?>) {
      ((MultiValueReference<T>) ref).setValues(bean, cli.getArguments());
    } else {
      String value = cli.getArguments().isEmpty() ? null : cli.getArguments().get(0);
      ((SingleValueReference<T>) ref).setValue(bean, value);
    }
  }
}
