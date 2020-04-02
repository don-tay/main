package nasa.model.module;

import static java.util.Objects.requireNonNull;
import static nasa.commons.util.AppUtil.checkArgument;

/**
 * Represents module code of a Module.
 * Guarantees: immutable; is valid as declared in {@link #isValidModuleCode(String)}
 */
public class ModuleCode {

    public static final String MESSAGE_CONSTRAINTS =
            "Module codes should only contain alphanumeric characters and spaces, and it should not be blank";

    /**
     * Module code should not contain any whitespace, and all values must be alphanumeric.
     */
    public static final String ALPHA_NUMERIC_VALIDATION_REGEX = "[a-zA-Z0-9]*";
    public static final String NON_EMPTY_STRING_VALIDATION_REGEX = "^(?=\\s*\\S).*$";

    public final String moduleCode;

    /**
     * Constructs a {@code ModuleCode}.
     *
     * @param moduleCode A valid module code.
     */
    public ModuleCode(String moduleCode) {
        requireNonNull(moduleCode);
        checkArgument(isValidModuleCode(moduleCode), MESSAGE_CONSTRAINTS);
        this.moduleCode = moduleCode.toUpperCase();
    }

    /**
     * Returns true if a given string is a valid module code.
     * @param test String
     * @return boolean
     */
    public static boolean isValidModuleCode(String test) {
        return test.matches(ALPHA_NUMERIC_VALIDATION_REGEX) && test.matches(NON_EMPTY_STRING_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return moduleCode;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ModuleCode // instanceof handles nulls
                && moduleCode.toLowerCase().equals(((ModuleCode) other)
            .moduleCode.toLowerCase())); // state check
    }

    @Override
    public int hashCode() {
        return moduleCode.hashCode();
    }
}
