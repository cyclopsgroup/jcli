package org.cyclopsgroup.jcli.spi;

/**
 * Model of argument definition
 *
 * @author <a href="mailto:jiaqi@cyclopsgroup.org">Jiaqi Guo</a>
 */
public interface Argument {
  /**
   * @return Description of arguments
   */
  String getDescription();

  /**
   * @return Displayable argument name in help page
   */
  String getDisplayName();
}
