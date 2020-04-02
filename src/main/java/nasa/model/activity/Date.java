package nasa.model.activity;

import static java.util.Objects.requireNonNull;
import static nasa.commons.util.AppUtil.checkArgument;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/**
 * Represents Date of an Activity.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}.
 */
public class Date {
    public static final String MESSAGE_CONSTRAINTS =
            "Dates should only be in the format DD-MM-YYYY HH:MM, and it should not be blank";

    public final LocalDateTime date;

    /**
     * Constructs a {@code Date}.
     *
     * @param date A valid date.
     */
    public Date(String date) {
        requireNonNull(date);
        checkArgument(isValidDate(date), MESSAGE_CONSTRAINTS);
        this.date = constructDateTime(date);
    }

    private static LocalDateTime constructDateTime(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        return LocalDateTime.parse(date, formatter);
    }

    /**
     * Construct current date.
     * @return Date object of current date
     */
    public static Date now() {
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
        return new Date(now);
    }

    /**
     * Checks if a given string is a valid date.
     * @param test String
     * @return true if the string matches date format.
     */
    public static boolean isValidDate(String test) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            formatter.setLenient(false);
            formatter.parse(test);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public boolean isAfter(Date other) {
        return date.isAfter(other.getDate());
    }

    public boolean isBefore(Date other) {
        return date.isBefore(other.getDate());
    }

    public boolean isEqual(Date other) {
        return date.isEqual(other.getDate());
    }

    public int getDifference(Date other) {
        Duration duration = Duration.between(other.getDate(), date);
        return (int) duration.toDaysPart();
    }

    /**
     * Constructs a new date from the current date and number of days to add to this current date.
     * @param numOfDaysToAdd number of days from the current day
     * @return a new instance of date
     */
    public Date addDaysToCurrDate(int numOfDaysToAdd) {
        LocalDateTime oldDateTime = this.getDate();
        LocalDateTime newDateTime = oldDateTime.plusDays(numOfDaysToAdd);
        String newDateTimeStr = newDateTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
        return new Date(newDateTimeStr);
    }

    /**
     * Constructs a new date from the current date and number of days to add to this current date.
     * @param numOfMonthsToAdd number of months from the current day
     * @return a new instance of date
     */
    public Date addMonthsToCurrDate(int numOfMonthsToAdd) {
        LocalDateTime oldDateTime = this.getDate();
        LocalDateTime newDateTime = oldDateTime.plusMonths(numOfMonthsToAdd);
        String newDateTimeStr = newDateTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
        return new Date(newDateTimeStr);
    }

    public LocalDateTime getDate() {
        return date;
    }

    /**
     * Returns date as a string with format MMM d yyyy.
     *
     * @return date as a string with format MMM d yyyy.
     */
    @Override
    public String toString() {
        return date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Date // instanceof handles nulls
                && date.equals(((Date) other).date)); // state check
    }

    @Override
    public int hashCode() {
        return date.hashCode();
    }
}

