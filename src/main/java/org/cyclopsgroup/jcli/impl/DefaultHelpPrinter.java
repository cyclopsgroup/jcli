package org.cyclopsgroup.jcli.impl;

import java.io.IOException;
import java.io.PrintWriter;
import org.cyclopsgroup.caff.format.Format;
import org.cyclopsgroup.caff.format.Formats;
import org.cyclopsgroup.jcli.spi.Option;
import com.google.common.base.Strings;

class DefaultHelpPrinter {
  static <T> void printHelp(AnnotationParsingContext<T> context, PrintWriter out)
      throws IOException {
    out.println("[USAGE]");
    out.println("  " + context.cli().getName() + (context.options().isEmpty() ? "" : " <OPTIONS>")
        + (context.argument() == null ? "" : " <ARGS>"));
    if (!Strings.isNullOrEmpty(context.cli().getDescription())) {
      out.println("[DESCRIPTION]");
      out.println("  " + context.cli().getDescription());
    }
    if (!context.options().isEmpty()) {
      out.println("[OPTIONS]");
      Format<OptionHelp> helpFormat = Formats.newFixLengthFormat(OptionHelp.class);
      for (Option option : context.options()) {
        String line = helpFormat.formatToString(new OptionHelp(option)).trim();
        out.println("  " + line);
      }
    }
    if (context.argument() != null) {
      out.println("[ARGS]");
      out.println("  <" + context.argument().getDisplayName() + ">... "
          + context.argument().getDescription());
    }
    if (!Strings.isNullOrEmpty(context.cli().getNote())) {
      out.println("[NOTE]");
      out.println("  " + context.cli().getNote());
    }
  }
}
