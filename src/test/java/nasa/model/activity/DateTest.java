package nasa.model.activity;

import static nasa.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class DateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Date(null));
    }

    @Test
    public void constructor_invalidDate_throwsIllegalArgumentException() {
        String invalidDate = "";
        assertThrows(IllegalArgumentException.class, () -> new Date(invalidDate));
    }

    @Test
    void isValidDate() {
        // null Date
        assertThrows(NullPointerException.class, () -> Date.isValidDate(null));

        // invalid Dates
        assertFalse(Date.isValidDate("")); // empty string
        assertFalse(Date.isValidDate(" ")); // spaces only
        assertFalse(Date.isValidDate("30-02-2020")); // invalid date
        assertFalse(Date.isValidDate("31-02-2020 12:00"));
        assertFalse(Date.isValidDate("29-02-2020")); //no time;

        // valid Dates
        assertTrue(Date.isValidDate("29-02-2020 23:59")); //leap year
        assertTrue(Date.isValidDate("29-2-2020 23:59")); //leap year

        assertTrue(Date.isValidDate("12-03-2020 20:00")); //leap year
    }

    @Test
    void getDifferenceInDate() {
        Date dateNoOne = new Date("20-04-2020 23:59");
        Date dateNoTwo = new Date("19-03-2020 01:00");

        long difference = dateNoOne.getDifference(dateNoTwo);

        System.out.println(difference);
    }

    @Test
    void testToString() {
    }

    @Test
    void testEquals() {
    }

    @Test
    void testHashCode() {
    }
}
