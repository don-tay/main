package nasa.model.activity;

import static nasa.commons.util.AppUtil.checkArgument;

/**
 * Represents an activity's priority.
 * Guarantees: an integer of range 1 to 5, default value set to 1 if no value received upon instantiation.
 */
public class Priority {

    public static final String MESSAGE_CONSTRAINTS = "Priority should range from 1 to 5 inclusive only.";

    /**
     * Valid integers that cannot start with the digit '0', and within range of 1 to 5.
     */
    public static final String VALID_INTEGER_REGEX = "([1-5]\\d{0})";

    /**
     * Priority level set to 1 as default value if not specified.
     * Variable is not set as final to allow user to set default priority level (TBD).
     */
    private static int defaultPriorityLevel = 1;

    private final int priorityLevel;

    /**
     * Constructs a {@code Priority} with default priority level, when no argument is passed.
     */
    public Priority() {
        this.priorityLevel = defaultPriorityLevel;
    }

    /**
     * Constructs a {@code Priority} with the value {@code priorityLevel}.
     *
     * @param priorityLevel priority level of the given activity, a String in the integer range of 1 to 5 inclusive.
     */
    public Priority(String priorityLevel) {
        checkArgument(isValidPriorityValue(priorityLevel), MESSAGE_CONSTRAINTS);
        this.priorityLevel = Integer.parseInt(priorityLevel);
    }

    public static int getDefaultPriorityLevel() {
        return defaultPriorityLevel;
    }

    /**
     * Returns true if a given priority level string is in the valid integer range of 1 to 5.
     * @param test String
     * @return boolean
     */
    public static boolean isValidPriorityValue(String test) {
        return test.matches(VALID_INTEGER_REGEX);
    }

    @Override
    public String toString() {
        return Integer.toString(priorityLevel);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Priority // instanceof handles nulls
                && priorityLevel == ((Priority) other).priorityLevel); // state check
    }

}
