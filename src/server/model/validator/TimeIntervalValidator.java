package server.model.validator;

import java.time.LocalTime;

public class TimeIntervalValidator {

    public static void set(LocalTime from, LocalTime to) {
        if (from == null) {
            throw new IllegalArgumentException("Please input a starting time");
        }
        if (to == null) {
            throw new IllegalArgumentException("Please input an end time");
        }
        if (to.isBefore(from)) {
            throw new IllegalArgumentException("The end time cannot be before the start time");
        }
    }
}
