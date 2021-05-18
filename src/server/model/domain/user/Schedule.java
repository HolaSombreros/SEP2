package server.model.domain.user;

import server.model.domain.appointment.TimeInterval;

import java.io.Serializable;
import java.time.LocalDate;

public class Schedule implements Serializable {
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private Shift shift;
    private int id;

    public Schedule(int id, LocalDate dateFrom, LocalDate dateTo, Shift shift) {
        if (dateFrom.isBefore(LocalDate.now()))
            throw new IllegalArgumentException("Not valid date");

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

    public String toString() {
        return dateFrom.toString() + " - " + dateTo.toString() + " " + shift.toString();
    }
}
