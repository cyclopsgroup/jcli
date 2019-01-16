package org.cyclopsgroup.jcli;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.cyclopsgroup.jcli.annotation.Argument;
import org.cyclopsgroup.jcli.annotation.Cli;
import org.cyclopsgroup.jcli.annotation.MultiValue;
import org.cyclopsgroup.jcli.annotation.Option;

@Cli(name = "sample", description = "A test")
public class Simple implements AutoCompletable {
  public List<String> suggestArgument(String partialArgument) {
    if (partialArgument == null) {
      return Arrays.asList("11111", "22222", "33333");
    } else {
      return null;
    }
  }

  public List<String> suggestOption(String optionName, String partialOption) {
    if (partialOption != null) {
      return null;
    }
    if (optionName.equals("i")) {
      return Arrays.asList("1", "2", "3", "4");
    } else if (optionName.equals("f")) {
      return Arrays.asList("aaaa", "bbbb", "cccc");
    } else {
      return null;
    }
  }

  private boolean booleanField;

  private int intField;

  private String stringField1;

  private String stringField2;

  private List<String> values;

  public final int getIntField() {
    return intField;
  }

  public final String getStringField1() {
    return stringField1;
  }

  public final String getStringField2() {
    return stringField2;
  }

  public final List<String> getValues() {
    return values;
  }

  public final boolean isBooleanField() {
    return booleanField;
  }

  @Option(name = "b", longName = "boolean", description = "Test boolean field")
  public final void setBooleanField(boolean booleanField) {
    this.booleanField = booleanField;
  }

  @Option(name = "i", longName = "tint", description = "Test int value")
  public final void setIntField(int intField) {
    this.intField = intField;
  }

  @Option(name = "f", longName = "field1", required = true)
  public final void setStringField1(String stringField1) {
    this.stringField1 = stringField1;
  }

  @Option(name = "2", longName = "field2")
  public final void setStringField2(String stringFIeld2) {
    this.stringField2 = stringFIeld2;
  }

  @MultiValue(listType = ArrayList.class)
  @Argument
  public final void setValues(List<String> values) {
    this.values = values;
  }

  @Override
  public String toString() {
    return ReflectionToStringBuilder.toString(this);
  }
}
