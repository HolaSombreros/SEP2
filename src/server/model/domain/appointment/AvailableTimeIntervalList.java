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
}
