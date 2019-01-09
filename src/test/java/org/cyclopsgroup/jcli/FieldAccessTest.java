package org.cyclopsgroup.jcli;

import static com.google.common.truth.Truth.assertThat;
import java.security.AccessController;
import java.util.Arrays;
import javax.annotation.Nullable;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.cyclopsgroup.jcli.annotation.Option;
import org.junit.Test;

public class FieldAccessTest {
  static class Options {
    @Option(name = "i", longName = "int_field")
    int intField;

    @Option(name = "s", longName = "string_field")
    String stringField;

    private Options() {}

    private Options(@Nullable String f1, int f2) {
      this.stringField = f1;
      this.intField = f2;
    }

    @Override
    public boolean equals(Object obj) {
      return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public int hashCode() {
      return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
      return ToStringBuilder.reflectionToString(this);
    }
  }

  @Test
  public void testProcessWithAllParameters() {
    assertThat(ArgumentProcessor.forType(Options.class).process(
        Arrays.asList("-i", "4", "--string_field", "abc"), new Options(),
        AccessController.getContext())).isEqualTo(new Options("abc", 4));
  }

  @Test
  public void testProcessWithDefault() {
    assertThat(ArgumentProcessor.forType(Options.class).process(Arrays.asList("--int_field", "4"),
        new Options(), AccessController.getContext())).isEqualTo(new Options(null, 4));
  }
}
