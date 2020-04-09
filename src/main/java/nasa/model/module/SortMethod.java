package nasa.model.module;

import static java.util.Objects.requireNonNull;
import static nasa.commons.util.AppUtil.checkArgument;
import java.util.Comparator;
import nasa.model.activity.Deadline;

/**
 * Represents a SortMethod in NASA.
 * Guarantees: immutable; name is valid as declared in {@link #isValidSortMethod(String)}
 */
public class SortMethod {

    public static final String MESSAGE_CONSTRAINTS = "Sort method must be either name, date, or priority.";
    private final String sortMethodString;
    private final Comparator<Deadline> comparator;

    /**
     * Constructs a {@code SortMethod}.
     * @param method A valid method of sorting.
     */
    public SortMethod(String method) {
        requireNonNull(method);
        checkArgument(isValidSortMethod(method), MESSAGE_CONSTRAINTS);
        this.sortMethodString = generateSortMethodString(method);
        this.comparator = generateComparator(method);
    }

    /**
     * Returns true if a given string is a valid method of sorting.
     */
    public static boolean isValidSortMethod(String test) {
        return (test.equals("name") || test.equals("date") || test.equals("priority"));
    }

    /**
     * Returns the string value of the sort method.
     * @return The string representation of the method of sorting.
     */
    public String getSortMethodString() {
        return sortMethodString;
    }

    /**
     * Returns the Comparator of the sort method.
     * @return The Comparator used to sort the deadline list.
     */
    public Comparator<Deadline> getComparator() {
        return comparator;
    }

    /**
     * Returns the comparator used to sort the deadline list.
     * @return The comparator of this instance of {@code SortMethod}.
     */
    public Comparator<Deadline> generateComparator(String method) {
        /*
        Default comparator, sorts in ascending order.
        Lexicographically smallest(a-z), earliest due date, highest priority at the top of the module deadline list.
         */
        Comparator<Deadline> nameSorter = Comparator.comparing(l -> l.getName().toString(),
                String.CASE_INSENSITIVE_ORDER);
        Comparator<Deadline> dateSorter = Comparator.comparing(l -> l.getDueDate().toString());
        Comparator<Deadline> prioritySorter = Comparator.comparing(l -> l.getPriority().toString(),
                Comparator.reverseOrder());

        switch (method) {
        case "name":
            return nameSorter;
        case "date":
            return dateSorter;
        case "priority":
            return prioritySorter;
        default:
            throw new IllegalStateException("Unexpected value: " + getSortMethodString());
        }
    }

    private String generateSortMethodString(String method) {
        if (method.equals("date")) {
            return "due date";
        }
        return method;
    }

    @Override
    public String toString() {
        return getSortMethodString();
    }

}
