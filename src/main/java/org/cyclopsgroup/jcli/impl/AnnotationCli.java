package org.cyclopsgroup.jcli.impl;

import javax.annotation.Nullable;
import org.cyclopsgroup.jcli.annotation.Cli;

/**
 * Annotation based Cli implementation
 *
 * @author <a href="mailto:jiaqi@cyclopsgroup.org">Jiaqi Guo</a>
 */
class AnnotationCli implements org.cyclopsgroup.jcli.spi.Cli {
  @Nullable
  private final Cli cli;

  private final boolean undefined;

  /**
   * @param cli Annotation cli
   */
  AnnotationCli(Cli cli) {
    this.cli = cli;
    this.undefined = cli == null;
  }

  @Override
  public String getDescription() {
    return undefined ? null : cli.description();
  }

  @Override
  public String getName() {
    return undefined ? null : cli.name();
  }

  @Override
  public String getNote() {
    return undefined ? null : cli.note();
  }
}
