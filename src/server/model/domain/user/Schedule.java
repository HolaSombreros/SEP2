package server.model.domain.user;

import java.io.Serializable;
import java.time.LocalDate;

public class Schedule implements Serializable {
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private Shift shift;
    private int id;

    public Schedule(int id, LocalDate dateFrom, LocalDate dateTo, Shift shift) {
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.shift = shift;
        this.id = id;
    }

    public void setDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }

    public void setDateTo(LocalDate dateTo) {
        this.dateTo = dateTo;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public void setShift(Shift shift) {
        this.shift = shift;
    }

    public Shift getShift() {
        return shift;
    }

    public int getId() {
        return id;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Schedule)) {
            return false;
        }
        Schedule schedule = (Schedule) obj;
        return schedule.id == id && schedule.dateFrom.equals(dateFrom) && schedule.dateTo.equals(dateTo) && schedule.shift.equals(shift);
    }

    @Override
    public String toString() {
        return dateFrom.toString() + " - " + dateTo.toString() + " " + shift.toString();
    }
}
