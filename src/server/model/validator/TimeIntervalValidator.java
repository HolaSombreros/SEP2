package server.model.validator;

import server.model.domain.Time;

public class TimeIntervalValidator {

    public static void set(Time from, Time to){
        if (from == null) {
            throw new IllegalArgumentException("Please input a starting time");
        }
        if (to == null) {
            throw new IllegalArgumentException("Please input an end time");
        }
        if(to.isBefore(from)){
            throw new IllegalArgumentException("The end time cannot be before the start time");
        }
    }
}
