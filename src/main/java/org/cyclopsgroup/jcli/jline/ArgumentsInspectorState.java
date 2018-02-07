package org.cyclopsgroup.jcli.jline;

/**
 * Internal inspection state
 * 
 * @author <a href="mailto:jiaqi.guo@gmail.com">Jiaqi Guo</a>
 */
enum ArgumentsInspectorState {
  /**
   * Ready for new option of argument
   */
  READY,
  /**
   * Writing short option name
   */
  OPTION,
  /**
   * Writing long option name
   */
  LONG_OPTION,
  /**
   * Writing option value
   */
  OPTION_VALUE,
  /**
   * Writing argument
   */
  ARGUMENT;
}
