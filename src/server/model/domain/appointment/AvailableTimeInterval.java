package server.model.domain.appointment;

import java.time.LocalDate;

public class AvailableTimeInterval {
    private LocalDate date;
    private TimeInterval timeInterval;
    private int amount;
    
    public AvailableTimeInterval(LocalDate date, TimeInterval timeInterval) {
        this.date = date;
        this.timeInterval = timeInterval;
        amount = 0;
    }
    
    public boolean has (Appointment appointment) {
        return appointment.getDate().equals(date) && appointment.getTimeInterval().equals(timeInterval);
    }
    
    public LocalDate getDate() {
        return date;
    }
    
    public TimeInterval getTimeInterval() {
        return timeInterval;
    }
    
    public void increase() {
        amount += 1;
    }
    
    public void decrease() {
        amount -= 1;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof AvailableTimeInterval)) {
            return false;
        }
        
        AvailableTimeInterval availableTimeInterval = (AvailableTimeInterval) obj;
        return availableTimeInterval.date.equals(date) && availableTimeInterval.timeInterval.equals(timeInterval) && availableTimeInterval.amount == amount;
    }
    
    @Override
    public String toString() {
        return "AvailableTimeInterval{" + "date=" + date + ", timeInterval=" + timeInterval + ", amount=" + amount + '}';
    }
}
