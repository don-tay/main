package nasa.model.activity;

import static java.util.Objects.requireNonNull;
import static nasa.commons.util.AppUtil.checkArgument;
import static nasa.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents Event method in Nasa Book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Event extends Activity {
    public static final String INVALID_EVENT =
        "Event provided is invalid!";

    private Date startDate;
    private Date endDate;

    /**
     * Initialise Event with default status and priority.
     * @param name Name
     * @param startDate Date
     * @param endDate Date
     */
    public Event(Name name, Date startDate, Date endDate) {
        super(name);
        requireAllNonNull(startDate, endDate);
        this.startDate = startDate;
        this.endDate = endDate;
        checkArgument(isValidEvent(this), INVALID_EVENT);
    }

    /**
     * Initialise Event with default status and priority.
     * @param name Name
     * @param note Note
     * @param startDate Date
     * @param endDate Date
     */
    public Event(Name name, Note note, Date startDate, Date endDate) {
        super(name, note);
        requireAllNonNull(startDate, endDate);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * Initialise Event with default status and priority.
     * @param name Name
     * @param note Note
     * @param priority Priority
     * @param startDate Date
     * @param endDate Date
     */
    public Event(Name name, Note note, Priority priority, Date startDate, Date endDate) {
        super(name, note, priority);
        requireAllNonNull(startDate, endDate);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * Initialise Event with a particular unique {@code name}.
     * Every field must be present and not null.
     * @param name Name
     * @param note Note
     * @param status Status
     * @param priority Priority
     * @param startDate Date
     * @param endDate Date
     */
    public Event(Name name, Note note, Status status, Priority priority, Date startDate, Date endDate) {
        super(name, Date.now(), note, status, priority);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * Initialise Event with a particular unique {@code name}.
     * Every field must be present and not null.
     * @param name Name
     * @param date Date
     * @param note Note
     * @param status Status
     * @param priority Priority
     * @param startDate Date
     * @param endDate Date
     */
    public Event(Name name, Date date, Note note, Status status, Priority priority, Date startDate, Date endDate) {
        super(name, date, note, status, priority);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Date getDateFrom() {
        return startDate;
    }

    public Date getDateTo() {
        return endDate;
    }

    /**
     * Checks if the event is valid.
     * @param activity Activity to be checked
     * @return true if it is valid else false
     */
    public static boolean isValidEvent(Activity activity) {
        requireNonNull(activity);

        if (activity instanceof Event) {
            Event event = (Event) activity;
            boolean hasNotExpired = Date.now().isBefore(event.getDateTo());
            boolean isLogical = event.getDateFrom().isBefore(event.getDateTo());
            return hasNotExpired && isLogical;
        } else {
            return false;
        }
    }

    //TODO: detailed implementation of event regeneration
    public Event regenerate() {
        return this;
    }

    @Override
    public boolean occurInMonth(int month) {
        int startDateMonth = startDate.getDate().getMonth().getValue();
        return month == startDateMonth;
    }
}
