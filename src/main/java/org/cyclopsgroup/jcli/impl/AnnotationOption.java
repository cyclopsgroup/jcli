package org.cyclopsgroup.jcli.impl;

import org.cyclopsgroup.jcli.annotation.Option;

class AnnotationOption implements org.cyclopsgroup.jcli.spi.Option {
  private final boolean flag;

  private final boolean multiValue;

  private final Option option;

  AnnotationOption(Option option, boolean flag, boolean multiValue) {
    this.option = option;
    this.flag = flag;
    this.multiValue = multiValue;
  }

  @Override
  public String getDefaultValue() {
    return option.defaultValue();
  }

  @Override
  public String getDescription() {
    return option.description();
  }

  @Override
  public String getDisplayName() {
    return option.displayName();
  }

  @Override
  public String getLongName() {
    return option.longName();
  }

  @Override
  public String getName() {
    return option.name();
  }

  @Override
  public boolean isFlag() {
    return flag;
  }

  @Override
  public boolean isMultiValue() {
    return multiValue;
  }

  @Override
  public boolean isRequired() {
    return option.required();
  }
}
