package server.model;

import java.io.Serializable;

public class TimeInterval implements Serializable {
    private Time from;
    private Time to;
    
    public TimeInterval(Time from, Time to) {
        this.from = from.copy();
        this.to = to.copy();
    }
    
    public Time getFrom() {
        return from.copy();
    }
    
    public Time getTo() {
        return to.copy();
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
