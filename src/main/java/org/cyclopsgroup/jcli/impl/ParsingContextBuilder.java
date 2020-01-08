package org.cyclopsgroup.jcli.impl;

import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import org.cyclopsgroup.caff.conversion.AnnotatedConverter;
import org.cyclopsgroup.caff.conversion.Converter;
import org.cyclopsgroup.caff.ref.ValueReference;
import org.cyclopsgroup.jcli.annotation.Argument;
import org.cyclopsgroup.jcli.annotation.Cli;
import org.cyclopsgroup.jcli.annotation.MultiValue;
import org.cyclopsgroup.jcli.annotation.Option;

/**
 * Internal builder to create instance of {@link AnnotationParsingContext}
 *
 * @author <a href="mailto:jiaqi@cyclopsgroup.org">Jiaqi Guo</a>
 * @param <T> Type of bean to parse
 */
class ParsingContextBuilder<T> {
  @SuppressWarnings("unchecked")
  private static <B, P> Reference<B> createReference(
      Class<B> beanType, ValueReference<B> reference, String longName) {
    Class<P> valueType = (Class<P>) reference.getType();
    MultiValue multiValue = reference.getAnnotation(MultiValue.class);
    if (multiValue != null) {
      valueType = (Class<P>) multiValue.valueType();
    }
    Converter<P> converter = new AnnotatedConverter<P>(valueType, reference.getAnontatedElements());
    if (multiValue != null) {
      return new MultiValueReference<B>(
          beanType, converter, reference, longName, multiValue.listType());
    }
    return new SingleValueReference<B>(beanType, converter, reference, longName);
  }

  /**
   * Get annotation from either writer or reader of a Java property
   *
   * @param <A> Type of annotation
   * @param descriptor Field descriptor from which annotation is searched
   * @param type Type of annotation
   * @return Annotation or null if it's not found
   */
  @Nullable
  private static <A extends Annotation> A getAnnotation(
      PropertyDescriptor descriptor, Class<A> type) {
    A a = null;
    if (descriptor.getWriteMethod() != null) {
      a = descriptor.getWriteMethod().getAnnotation(type);
    }
    if (a == null && descriptor.getReadMethod() != null) {
      a = descriptor.getReadMethod().getAnnotation(type);
    }
    return a;
  }

  private static <T> List<ValueReference<T>> referenceOfDescriptors(Class<T> beanType) {
    BeanInfo beanInfo;
    try {
      beanInfo = Introspector.getBeanInfo(beanType);
    } catch (IntrospectionException e) {
      throw new RuntimeException("Bean " + beanType + " is not correctly defined", e);
    }
    return Arrays.asList(beanInfo.getPropertyDescriptors()).stream()
        .map(d -> ValueReference.<T>instanceOf(d))
        .collect(Collectors.toList());
  }

  private static <T> ImmutableList<ValueReference<T>> referenceOfFields(Class<T> beanType) {
    Map<String, ValueReference<T>> refMap = new HashMap<>();
    FluentIterable.from(beanType.getFields()).append(beanType.getDeclaredFields()).toList().stream()
        .map(f -> ValueReference.<T>instanceOf(f))
        .forEach(f -> refMap.put(f.getName(), f));
    return ImmutableList.copyOf(refMap.values());
  }

  private final Class<T> beanType;

  @SuppressWarnings("unchecked")
  ParsingContextBuilder(Class<? extends T> beanType) {
    this.beanType = (Class<T>) beanType;
  }

  AnnotationParsingContext<T> build() {
    List<AnnotationOption> options = new ArrayList<AnnotationOption>();
    Map<String, Reference<T>> references = new HashMap<String, Reference<T>>();
    Argument argument = null;
    List<ValueReference<T>> writableReferences =
        FluentIterable.from(referenceOfDescriptors(beanType))
            .append(referenceOfFields(beanType))
            .filter(r -> r.isWritable())
            .toList();

    for (ValueReference<T> ref : writableReferences) {
      Option option = ref.getAnnotation(Option.class);
      if (option != null) {
        boolean flag = (ref.getType() == Boolean.TYPE || ref.getType() == Boolean.class);
        boolean multiValue = ref.getAnnotation(MultiValue.class) != null;
        options.add(new AnnotationOption(option, flag, multiValue));
        references.put(option.name(), createReference(beanType, ref, option.longName()));
        continue;
      }
      Argument arg = ref.getAnnotation(Argument.class);
      if (arg != null) {
        argument = arg;
        references.put(
            DefaultArgumentProcessor.ARGUMENT_REFERNCE_NAME,
            createReference(beanType, ref, DefaultArgumentProcessor.ARGUMENT_REFERNCE_NAME));
        continue;
      }
    }

    return new AnnotationParsingContext<T>(
        references,
        options,
        new AnnotationCli(beanType.getAnnotation(Cli.class)),
        argument == null ? null : new AnnotationArgument(argument));
  }
}
