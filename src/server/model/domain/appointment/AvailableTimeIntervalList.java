package server.model.domain.appointment;

import java.util.ArrayList;
import java.util.List;

public class AvailableTimeIntervalList {
    private List<AvailableTimeInterval> availableTimeIntervals;
    
    public AvailableTimeIntervalList() {
        availableTimeIntervals = new ArrayList<>();
    }
    
    public void add(AvailableTimeInterval availableTimeInterval) {
        availableTimeIntervals.add(availableTimeInterval);
    }
    
    @Override
    public String toString() {
        String str = "List: ";
        for (AvailableTimeInterval a : availableTimeIntervals) {
            str += "\n\t" + a;
        }
        return str;
    }
}
