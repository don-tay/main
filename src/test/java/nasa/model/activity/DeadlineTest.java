package nasa.model.activity;

import static nasa.testutil.TypicalActivities.DEADLINE;
import static nasa.testutil.TypicalActivities.DEADLINE_LATE;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import nasa.testutil.Assert;

class DeadlineTest {

    @Test
    void create_deadline() {
        Deadline subject = new Deadline(new Name("Test"), new Date("20-04-2020 03:00"), new Note("Practice"));
        subject.updateStatus();
        assertFalse(subject.isLate());
    }

    @Test
    void check_format_deadline() {
        Assert.assertThrows(IllegalArgumentException.class, () ->
                new Deadline(new Name("Test"), new Date("20-04-2019 03:00"), new Note("Practice")));
    }

    @Test
    void set_due_date() {
        Date date = new Date("19-03-2020 03:00");
        Deadline subject = (Deadline) DEADLINE_LATE;

        assertFalse(subject.getDueDate().equals(date));

        subject.setDueDate(date);
        assertTrue(subject.getDueDate().equals(date));
    }

    @Test
    void percentage() {
        Deadline subject = (Deadline) DEADLINE;
        subject.setDate(new Date("20-03-2020 00:00"));
        subject.setDueDate(new Date("30-03-2020 03:00"));
        Date currentDate = new Date("26-03-2020 03:00");

        assertTrue(subject.percentage(currentDate) > 50);
    }
}
