package server.model.domain.appointment;

import server.model.validator.TimeIntervalValidator;

import java.io.Serializable;
import java.time.LocalTime;

public class TimeInterval implements Serializable {
    private LocalTime from;
    private LocalTime to;
    private int id;

    public TimeInterval(int id, LocalTime from, LocalTime to) {
        set(from, to, id);
    }

    public void set(LocalTime from, LocalTime to, int id) {
        TimeIntervalValidator.set(from, to);
        this.from = from;
        this.to = to;
        this.id = id;
    }

    public int getId() {
        return id;
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
                to.equals(timeInterval.to) && id == timeInterval.id;
    }

    public String toString() {
        return from.toString() + " - " + to.toString();
    }
}
