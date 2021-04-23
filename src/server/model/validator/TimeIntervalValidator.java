package server.model.validator;

import server.model.domain.Time;

public class TimeIntervalValidator {

    public static void set(Time from, Time to){
        if(to.isBefore(from)){
            throw new IllegalArgumentException("Time to cannot be before time from");
        }
        if(from == null || to == null){
            throw new IllegalArgumentException("Time to or from cannot be null");
        }
    }
}
