package server.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TimeIntervalList implements Serializable {
    private List<TimeInterval> timeIntervals;
    
    public TimeIntervalList() {
        timeIntervals = new ArrayList<>();
    }
    
    public List<TimeInterval> getTimeIntervals() {
        return timeIntervals;
    }
    
    public void add(TimeInterval timeInterval) {
        timeIntervals.add(timeInterval);
    }
    
    public boolean contains(TimeInterval timeInterval) {
        return timeIntervals.contains(timeInterval);
    }
    
    public void remove(TimeInterval timeInterval) {
        timeIntervals.remove(timeInterval);
    }
}
