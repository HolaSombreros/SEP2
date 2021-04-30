package server.model.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TimeTest {
    private Time time;

    @BeforeEach
    void setUp() {
        time = new Time(8, 5);
    }

    private String stringVal() {
        return time.toString();
    }

    @Test
    void setMinuteZero() {
        time.setMinute(0);
        assertEquals(stringVal(), "08:00");
    }

    @Test
    void setMinuteOne() {
        time.setMinute(1);
        assertEquals(stringVal(), "08:01");
    }

    @Test
    void setMinuteMany() {
        time.setMinute(32);
        assertEquals(stringVal(), "08:32");

        time.setMinute(17);
        assertEquals(stringVal(), "08:17");
    }

    @Test
    void setMinuteBoundary() {
        // lower left
        assertThrows(IllegalArgumentException.class, () -> time.setMinute(-1));

        // upper left done in Zero()

        // lower right
        time.setMinute(59);
        assertEquals(stringVal(), "08:59");

        // upper right
        assertThrows(IllegalArgumentException.class, () -> time.setMinute(60));
    }

    @Test
    void setMinuteException() {
        // done in Boundary()
    }

    @Test
    void setHourZero() {
        time.setHour(0);
        assertEquals(stringVal(), "00:05");
    }

    @Test
    void setHourOne() {
        time.setHour(1);
        assertEquals(stringVal(), "01:05");
    }

    @Test
    void setHourMany() {
        time.setHour(13);
        assertEquals(stringVal(), "13:05");

        time.setHour(21);
        assertEquals(stringVal(), "21:05");
    }

    @Test
    void setHourBoundary() {
        // lower left
        assertThrows(IllegalArgumentException.class, () -> time.setHour(-1));

        // upper left done in Zero()

        // lower right
        time.setHour(23);
        assertEquals(stringVal(), "23:05");

        // upper right
        assertThrows(IllegalArgumentException.class, () -> time.setHour(24));
    }

    @Test
    void setHourException() {
        // done in Boundary()
    }
}