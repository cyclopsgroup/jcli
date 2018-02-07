package org.cyclopsgroup.jcli.impl;

import org.cyclopsgroup.jcli.annotation.Argument;

class AnnotationArgument implements org.cyclopsgroup.jcli.spi.Argument {
  private Argument argument;

  /**
   * @param argument Argument annotation
   */
  AnnotationArgument(Argument argument) {
    this.argument = argument;
  }

  @Override
  public String getDescription() {
    return argument.description();
  }

  @Override
  public String getDisplayName() {
    return argument.displayName();
  }
}
