package nasa.model.activity;

import static nasa.commons.util.AppUtil.checkArgument;
import static nasa.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents Lesson method in Nasa book. New lesson re-instantiates itself after the current lesson's status is DONE.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Lesson extends Activity {
    public static final String INVALID_LESSON =
        "Lesson provided is invalid!";

    private int numOfDaysTillNextLesson = 7; // Frequency of lesson in number of days (eg. every 7 days) Default is 7.
    private Date startDate;
    private Date endDate;

    /**
     * Initialise Lessons with default status and priority.
     * @param name Name
     * @param startDate Date
     * @param endDate Date
     */
    public Lesson(Name name, Date startDate, Date endDate) {
        super(name);
        requireAllNonNull(startDate, endDate);
        this.startDate = startDate;
        this.endDate = endDate;
        checkArgument(isValidLesson(this), INVALID_LESSON);
    }

    /**
     * Initialise Lesson with a note.
     * @param name Name
     * @param note Note
     * @param startDate Date
     * @param endDate Date
     */
    public Lesson(Name name, Note note, Date startDate, Date endDate) {
        super(name, note);
        requireAllNonNull(startDate, endDate);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * Initialise Lesson with priority.
     * @param name Name
     * @param note Note
     * @param priority Priority
     * @param startDate Date
     * @param endDate Date
     */
    public Lesson(Name name, Note note, Priority priority, Date startDate, Date endDate) {
        super(name, note, priority);
        requireAllNonNull(startDate, endDate);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * Initialise Deadlines with a particular unique {@code name}.
     * Every field must be present and not null.
     * @param name Name
     * @param date Date
     * @param note Note
     * @param status Status
     * @param priority Priority
     * @param startDate Date
     * @param endDate Date
     */
    public Lesson(Name name, Date date, Note note, Status status, Priority priority, Date startDate, Date endDate) {
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

    public int getNumOfDaysTillNextLesson() {
        return numOfDaysTillNextLesson;
    }

    public void setNumOfDaysTillNextLesson(int newNumOfDays) {
        this.numOfDaysTillNextLesson = newNumOfDays;
    }

    /**
     * Initialise new lesson with the new date.
     * @return new instance of lesson at the new date
     */
    public Lesson regenerate() {
        return this;
    }

    @Override
    public boolean occurInMonth(int month) {
        int startDateMonth = startDate.getDate().getMonth().getValue();
        return month == startDateMonth;
    }

    /**
     * Method to check if the lesson is valid.
     * @param activity activity to be validated
     * @return true if the lesson is valid else false
     */
    public static boolean isValidLesson(Activity activity) {
        requireAllNonNull(activity);

        if (activity instanceof Lesson) {
            Lesson lesson = (Lesson) activity;
            boolean hasNotExpired = Date.now().isBefore(lesson.getDateTo());
            boolean isLogical = lesson.getDateFrom().isBefore(lesson.getDateTo());
            return isLogical && hasNotExpired;
        } else {
            return false;
        }
    }
}
