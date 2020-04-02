package nasa.model.activity;

import static nasa.commons.util.AppUtil.checkArgument;

/**
 * Represents schedule class in Nasa book.
 * Allows user to regenerate activity automatically.
 */
public class Schedule {

    public static final String MESSAGE_CONSTRAINTS = "Schedule should be from 0 to 3 inclusive only.";

    /**
     * Valid integers that start from 0 to 3.
     */
    public static final String VALID_INTEGER_REGEX = "([0-3]\\d{0})";

    private int type;
    private Date date;
    private Date defaultDate;

    /**
     * Construct a schedule from string.
     * @param input String
     */
    public Schedule(String input) {
        String[] in = input.split(",");
        type = Integer.parseInt(in[0]);
        date = new Date(in[1]);
        defaultDate = new Date(in[2]);
    }

    /**
     * Initialise schedule with the default type of 0.
     * @param date Date
     */
    public Schedule(Date date) {
        this.date = date;
        this.defaultDate = date;
        type = 0;
    }

    /**
     * Initialise schedule with specific type.
     * @param date Date
     * @param type int
     */
    public Schedule(Date date, int type) {
        checkArgument(isValidSchedule(String.valueOf(type)), MESSAGE_CONSTRAINTS);
        this.date = date;
        this.defaultDate = date;
        this.type = type;
        init(type);
    }

    /**
     * Method to extend the date of a module activity.
     * @return boolean
     */
    public boolean update() {
        boolean hasUpdate = false;
        while (Date.now().isAfter(date) && type != 0) {
            init(type);
            hasUpdate = true;
        }
        return hasUpdate;
    }

    /**
     * Set scheduling.
     * @param type int
     */
    public void setType(int type) {
        checkArgument(isValidSchedule(String.valueOf(type)), MESSAGE_CONSTRAINTS);
        this.type = type;
        date = defaultDate;
        update();
    }

    /**
     * Stop scheduling.
     */
    public void cancel() {
        this.type = 0;
        date = defaultDate;
    }

    /**
     * Initialise schedules.
     * @param type int
     */
    public void init(int type) {
        switch (type) {
        case 0 :
            cancel();
            break;
        case 1 :
            runOnceAWeek();
            break;
        case 2 :
            runTwiceAWeek();
            break;
        case 3 :
            runMonthly();
            break;
        default:
            break;
        }
    }

    /**
     * Get type of schedule.
     * @return int
     */
    public int getType() {
        return type;
    }

    /**
     * Get next running date.
     * @return Date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Set new date by refreshing it weekly.
     */
    private void runOnceAWeek() {
        date = date.addDaysToCurrDate(7);
    }

    /**
     * Set new date by refreshing it twice weekly.
     */
    private void runTwiceAWeek() {
        date = date.addDaysToCurrDate(14);
    }

    /**
     * Set new date by refreshing it monthly.
     */
    private void runMonthly() {
        date = date.addMonthsToCurrDate(1);
    }

    private static boolean isValidSchedule(String test) {
        return test.matches(VALID_INTEGER_REGEX);
    }

    @Override
    public String toString() {
        return String.format("%d,%s,%s", type, date, defaultDate);
    }

}
