package org.cyclopsgroup.jcli;

import java.util.List;
import org.cyclopsgroup.jcli.spi.CommandLine;
import org.cyclopsgroup.jcli.spi.CommandLineBuilder;
import org.cyclopsgroup.jcli.spi.CommandLineParser;
import org.cyclopsgroup.jcli.spi.Option;
import org.cyclopsgroup.jcli.spi.ParsingContext;

/** @author <a href="mailto:jiaqi@cyclopsgroup.org">Jiaqi Guo</a> */
public class GnuParser implements CommandLineParser {
  @Override
  public CommandLine parse(List<String> arguments, ParsingContext context) {
    CommandLineBuilder builder = new CommandLineBuilder();
    boolean expectingOptionValue = false;
    String optionName = null;
    boolean shortOption = false;
    for (String arg : arguments) {
      if (expectingOptionValue) {
        if (shortOption) {
          builder.withShortOption(optionName, arg);
        } else {
          builder.withLongOption(optionName, arg);
        }
        expectingOptionValue = false;
      } else if (arg.startsWith("--")) {
        optionName = arg.substring(2);
        Option opt = context.optionWithLongName(optionName);
        if (opt == null) {
          builder.withArgument(arg);
        } else if (opt.isFlag()) {
          builder.withLongFlag(optionName);
        } else {
          expectingOptionValue = true;
          shortOption = false;
        }
      } else if (arg.startsWith("-")) {
        optionName = arg.substring(1);
        Option opt = context.optionWithShortName(optionName);
        if (opt == null) {
          builder.withArgument(arg);
        } else if (opt.isFlag()) {
          builder.withShortFlag(optionName);
        } else {
          expectingOptionValue = true;
          shortOption = true;
        }
      } else {
        builder.withArgument(arg);
      }
    }
    return builder.toCommandLine();
  }
}
