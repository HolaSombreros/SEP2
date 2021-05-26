package server.model.domain.appointment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;


import static org.junit.jupiter.api.Assertions.*;

class TimeIntervalTest {
    private TimeInterval interval;
    private LocalTime timeFrom;
    private LocalTime timeTo;


    @BeforeEach
    void setUp() {
        timeFrom =  LocalTime.of(8, 0);
        timeTo = LocalTime.of(9, 20);
        interval = new TimeInterval(1,timeFrom, timeTo);
    }

    private String stringVal() {
        return interval.toString();
    }

    @Test
    void setFromNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            interval.set(null, LocalTime.of(8, 0), 1);
        });
    }

    @Test
    void setToNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            interval.set(LocalTime.of(8, 0), null,1);
        });
    }

    @Test
    void setToBeforeFrom() {
        assertThrows(IllegalArgumentException.class, () -> {
            interval.set(LocalTime.of(9, 0), LocalTime.of(8, 0), 1);
        });
    }

}