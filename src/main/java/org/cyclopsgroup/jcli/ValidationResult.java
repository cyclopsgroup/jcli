package org.cyclopsgroup.jcli;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.google.common.base.Preconditions;

/**
 * Argument validation result coming from {@link ArgumentProcessor#validate(String[])}
 */
public final class ValidationResult {
  /**
   * A violation indicating required argument is missing
   */
  public static final class ArgumentMissing extends Violation {
  }

  /**
   * Type of violation where a required option is missing
   */
  public static final class OptionMissing extends Violation {
    private final String optionName;

    /**
     * @param optionName Name of missing option
     */
    public OptionMissing(String optionName) {
      this.optionName =
          Preconditions.checkNotNull(optionName, "Name of missing option can't be null.");
    }

    /**
     * Get name of missing option
     *
     * @return optionName Name of option missed
     */
    public String getOptionName() {
      return optionName;
    }
  }

  /**
   * Violation where an unexpected option value is found
   */
  public static final class UnexpectedOption extends Violation {
    private final String optionName;

    /**
     * @param optionName Name of unexpected option
     */
    public UnexpectedOption(String optionName) {
      this.optionName =
          Preconditions.checkNotNull(optionName, "Name of missing option can't be null.");
    }

    /**
     * Get name of missing option
     *
     * @return optionName Name of option missed
     */
    public String getOptionName() {
      return optionName;
    }
  }

  /**
   * A validation violation
   */
  public static abstract class Violation {
    Violation() {}
  }

  private final List<Violation> violations = new ArrayList<Violation>();

  /**
   * Add a new violation to validation result
   *
   * @param <T> Type of validation violation
   * @param violation Violation
   */
  public <T extends Violation> void addViolation(T violation) {
    violations.add(violation);
  }

  /**
   * Get list of violations in result
   *
   * @return List of violations in result
   */
  public List<Violation> getViolations() {
    return Collections.unmodifiableList(violations);

  }

  /**
   * @return True if there is not violation
   */
  public boolean isValid() {
    return violations.isEmpty();
  }
}
