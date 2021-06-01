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
        if (getByAvailableTimeInterval(availableTimeInterval) == null)
            availableTimeIntervals.add(availableTimeInterval);
        else getByAvailableTimeInterval(availableTimeInterval).increaseMax();
    }

    public void remove(AvailableTimeInterval availableTimeInterval) {
        if (getByAvailableTimeInterval(availableTimeInterval).getMaxAmount() == 3)
            availableTimeIntervals.remove(availableTimeInterval);
        else getByAvailableTimeInterval(availableTimeInterval).decreaseMax();
    }

    public List<AvailableTimeInterval> getIntervals() {
        return availableTimeIntervals;
    }
    
    /**
     * Method to get a list of available time intervals of a given date.
     * @param date The date to check for.
     * @return A list of available time intervals.
     */
    public TimeIntervalList getByDate(LocalDate date) {
        TimeIntervalList intervals = new TimeIntervalList();
        if (date.isBefore(LocalDate.now()))
            return intervals;
        for (AvailableTimeInterval timeInterval : availableTimeIntervals) {
            if (date.equals(timeInterval.getDate()) && !date.equals(LocalDate.now()))
                if (timeInterval.isAvailable())
                    intervals.add(timeInterval.getTimeInterval());
            if (date.equals(timeInterval.getDate()) && date.equals(LocalDate.now()))
                if (!timeInterval.getTimeInterval().getFrom().isBefore(LocalTime.now()))
                    if (timeInterval.isAvailable())
                        intervals.add(timeInterval.getTimeInterval());
        }
        return intervals;
    }

    public AvailableTimeInterval getByAvailableTimeInterval(AvailableTimeInterval availableTimeInterval) {
        for (AvailableTimeInterval interval : availableTimeIntervals)
            if (interval.getDate().equals(availableTimeInterval.getDate()) && interval.getTimeInterval().equals(availableTimeInterval.getTimeInterval()))
                return interval;
        return null;
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
