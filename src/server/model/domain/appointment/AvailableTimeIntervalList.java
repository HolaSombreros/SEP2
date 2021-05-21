package server.model.domain.appointment;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class AvailableTimeIntervalList {
    private List<AvailableTimeInterval> availableTimeIntervals;
    
    public AvailableTimeIntervalList() {
        availableTimeIntervals = new ArrayList<>();
    }
    
    public void add(AvailableTimeInterval availableTimeInterval) {
        if (!availableTimeIntervals.contains(availableTimeInterval))
            availableTimeIntervals.add(availableTimeInterval);
        // TODO increase and ----- remove method
    }

    public TimeIntervalList getByDate(LocalDate date) {
        TimeIntervalList intervals = new TimeIntervalList();
        if (date.isBefore(LocalDate.now()))
            return intervals;
        for (AvailableTimeInterval timeInterval : availableTimeIntervals) {
            if (date.equals(timeInterval.getDate()) && !date.equals(LocalDate.now()))
                intervals.add(timeInterval.getTimeInterval());
            if (date.equals(timeInterval.getDate()) && date.equals(LocalDate.now()))
                if (!timeInterval.getTimeInterval().getFrom().isBefore(LocalTime.now()))
                    intervals.add(timeInterval.getTimeInterval());
        }
        return intervals;
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
