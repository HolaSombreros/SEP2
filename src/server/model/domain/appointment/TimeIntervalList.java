package server.model.domain.appointment;

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

    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof TimeIntervalList))
            return false;
        TimeIntervalList other = (TimeIntervalList) obj;
        if(other.timeIntervals.size() != timeIntervals.size())
            return false;
        for(int i =0; i < timeIntervals.size();i++){
            if(!timeIntervals.get(i).equals(other.timeIntervals.get(i)))
                return false;
        }
        return true;
    }

    @Override
    public String toString(){
        String all = "";
        for(TimeInterval timeInterval : timeIntervals){
            all += timeInterval.toString() + " \n";
        }
        return all;
    }
}
