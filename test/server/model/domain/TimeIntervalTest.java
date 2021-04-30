package server.model.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TimeIntervalTest {
    private TimeInterval interval;
    private Time timeFrom;
    private Time timeTo;

    @BeforeEach
    void setUp() {
        timeFrom = new Time(8, 0);
        timeTo = new Time(9, 20);
        interval = new TimeInterval(timeFrom, timeTo);
    }

    private String stringVal() {
        return interval.toString();
    }

    @Test
    void setFromNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            interval.set(null, new Time(8, 0));
        });
    }

    @Test
    void setToNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            interval.set(new Time(8, 0), null);
        });
    }

    @Test
    void setToBeforeFrom() {
        assertThrows(IllegalArgumentException.class, () -> {
            interval.set(new Time(10, 0), new Time(9, 0));
        });
    }
}