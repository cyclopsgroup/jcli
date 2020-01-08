package org.cyclopsgroup.jcli.impl;

import com.google.common.base.Strings;
import org.cyclopsgroup.caff.format.FixLengthField;
import org.cyclopsgroup.caff.format.FixLengthType;
import org.cyclopsgroup.jcli.spi.Option;

/**
 * An internal POJO to help printing out usage page
 *
 * @author <a href="mailto:jiaqi@cyclopsgroup.org">Jiaqi Guo</a>
 */
@FixLengthType(length = 256)
public class OptionHelp {
  private final Option option;

  OptionHelp(Option option) {
    this.option = option;
  }

  /** @return Description of option */
  @FixLengthField(start = 30, length = 220)
  public String getDescription() {
    String desc = option.getDescription();
    if (!option.isFlag() && !Strings.isNullOrEmpty(option.getDefaultValue())) {
      desc += "(Default value is " + option.getDefaultValue() + ")";
    }
    return desc;
  }

  /** @return Name of option value */
  @FixLengthField(start = 20, length = 9)
  public String getDisplayName() {
    return option.isFlag() ? null : "<" + option.getDisplayName() + ">";
  }

  /** @return Long name of option */
  @FixLengthField(start = 3, length = 16)
  public String getLongName() {
    return Strings.isNullOrEmpty(option.getLongName()) ? null : "--" + option.getLongName();
  }

  /** @return Short name of option */
  @FixLengthField(start = 0, length = 2)
  public String getName() {
    return "-" + option.getName();
  }
}
