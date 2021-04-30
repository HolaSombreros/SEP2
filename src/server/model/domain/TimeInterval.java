package server.model.domain;

import server.model.validator.TimeIntervalValidator;

import java.io.Serializable;
import java.time.LocalTime;

public class TimeInterval implements Serializable {
    private LocalTime from;
    private LocalTime to;
    
    public TimeInterval(LocalTime from, LocalTime to) {
        set(from, to);
    }
    public void set(LocalTime from, LocalTime to){
        TimeIntervalValidator.set(from, to);
        this.from = from;
        this.to = to;
    }

    public LocalTime getFrom() {
        return from;
    }
    
    public LocalTime getTo() {
        return to;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof TimeInterval)) {
            return false;
        }
        TimeInterval timeInterval = (TimeInterval) obj;
        return from.equals(timeInterval.from) &&
            to.equals(timeInterval.to);
    }

    public String toString(){
        return from.toString() + " - " + to.toString();
    }
}
