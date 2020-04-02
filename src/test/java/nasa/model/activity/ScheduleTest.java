package nasa.model.activity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class ScheduleTest {

    final Date date = new Date("10-03-2020 03:00");
    final Date temp = date;
    final Schedule schedule = new Schedule(date);

    @Test
    void initialisation() {
        assertEquals(date, schedule.getDate());
        assertEquals(0, schedule.getType());
    }

    @Test
    void checkChangeType() {
        schedule.setType(1);
        //System.out.println(schedule.getDate());
        assertTrue(Date.now().isBefore(schedule.getDate()));

        schedule.setType(2);
        //System.out.println(schedule.getDate());
        assertTrue(Date.now().isBefore(schedule.getDate()));

        schedule.setType(3);
        assertTrue(Date.now().isBefore(schedule.getDate()));

        schedule.cancel();
        assertEquals(temp, schedule.getDate());
    }

    @Test
    void checkWrongSchedule() {
        assertThrows(IllegalArgumentException.class, () -> schedule.setType(-1));
        assertThrows(IllegalArgumentException.class, () -> schedule.setType(4));
    }

}
