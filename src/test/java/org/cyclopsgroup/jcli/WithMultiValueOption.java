package org.cyclopsgroup.jcli;

import java.util.ArrayList;
import java.util.List;
import org.cyclopsgroup.jcli.annotation.Cli;
import org.cyclopsgroup.jcli.annotation.MultiValue;
import org.cyclopsgroup.jcli.annotation.Option;

/**
 * A testing bean with multi value option
 *
 * @author <a href="mailto:jiaqi.guo@gmail.com">Jiaqi Guo</a>
 */
@Cli(name = "sample")
public class WithMultiValueOption {
  private List<String> options;

  /** @return List value of option */
  public final List<String> getOptions() {
    return options;
  }

  /** @param options Option values */
  @Option(name = "o", longName = "option", description = "Multi value option")
  @MultiValue(listType = ArrayList.class)
  public final void setOptions(List<String> options) {
    this.options = options;
  }
}
