package nasa.model.activity;

import static java.util.Objects.requireNonNull;
import static nasa.commons.util.AppUtil.checkArgument;

/**
 * Represents an activity's notes.
 * Guarantees: immutable, is valid as declared in {@link #isValidNote(String)}
 */
public class Note {

    public static final String MESSAGE_CONSTRAINTS =
            "Notes should not be empty and must contain"
                    + "at least one non-whitespace character.";

    /*
     * The notes String must contain at least one non-whitespace
     * character, otherwise a bunch of tabs and spaces will
     * qualify as a note which should not be the case.
     */
    public static final String VALIDATION_REGEX = "^(?!\\s*$).+";

    public static final String VALIDATION_REGEX_EMPTY = "^$";

    public final String content;

    /**
     * Constructs a {@code Note}.
     *
     * @param content Valid notes.
     */
    public Note(String content) {
        requireNonNull(content);
        checkArgument(isValidNote(content), MESSAGE_CONSTRAINTS);
        this.content = content;
    }

    /**
     * Returns true if a given string is considered valid notes.
     * @param test String
     * @return boolean
     */
    public static boolean isValidNote(String test) {
        return test.trim().matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return content;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Note // instanceof handles nulls
                && content.equals(((Note) other).content)); // state check
    }

    @Override
    public int hashCode() {
        return content.hashCode();
    }
}
