package org.cyclopsgroup.jcli;

import java.util.List;

/**
 * Interface for CLI implementation that knows to suggest options
 * 
 * @author <a href="mailto:jiaqi.guo@gmail.com">Jiaqi Guo</a>
 */
public interface AutoCompletable {
  /**
   * Suggest candidates for an option with given partial input
   * 
   * @param optionName Name of option
   * @param partialOption Given partial input
   * @return List of candidates or NULL if it can't figure out
   */
  List<String> suggestOption(String optionName, String partialOption);

  /**
   * Suggest candidates for argument with given partial input
   * 
   * @param partialArgument Partial argument input
   * @return List of candidates or NULL if it can't figure out
   */
  List<String> suggestArgument(String partialArgument);
}
