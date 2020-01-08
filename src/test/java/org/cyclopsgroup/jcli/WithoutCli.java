package org.cyclopsgroup.jcli;

import org.cyclopsgroup.jcli.annotation.Option;

/** A bean with out cli annotation */
public class WithoutCli {
  String optionA;

  String optionWithDash;

  /** @param value A string option */
  @Option(name = "a")
  public void setOptionA(String value) {
    this.optionA = value;
  }

  /** @param value A string option with dash in name */
  @Option(name = "d", longName = "with-dash")
  public void setOptionWithDash(String value) {
    this.optionWithDash = value;
  }
}
