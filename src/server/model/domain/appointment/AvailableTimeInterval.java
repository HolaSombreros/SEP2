package server.model.domain.appointment;

import java.time.LocalDate;

public class AvailableTimeInterval {
    private LocalDate date;
    private TimeInterval timeInterval;
    private int amount;
    private int maxAmount;
    
    public AvailableTimeInterval(LocalDate date, TimeInterval timeInterval) {
        this.date = date;
        this.timeInterval = timeInterval;
        amount = 0;
        maxAmount = 3;
    }
    
    public boolean has(Appointment appointment) {
        if (appointment == null)
            return false;
        return appointment.getDate().equals(date) && appointment.getTimeInterval().equals(timeInterval);
    }
    
    public LocalDate getDate() {
        return date;
    }
    
    public TimeInterval getTimeInterval() {
        return timeInterval;
    }

    public int getAmount() {
        return amount;
    }
    
    public void increaseAmount() {
        amount += 1;
    }
    
    public void decreaseAmount() {
        amount -= 1;
    }

    public int getMaxAmount() {
        return maxAmount;
    }

    public void increaseMax() {
        maxAmount = maxAmount + 3;
    }

    public void decreaseMax() {
        maxAmount = maxAmount - 3;
    }

    public boolean isAvailable() {
        return amount < maxAmount;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof AvailableTimeInterval)) {
            return false;
        }
        
        AvailableTimeInterval availableTimeInterval = (AvailableTimeInterval) obj;
        return availableTimeInterval.date.equals(date) && availableTimeInterval.timeInterval.equals(timeInterval) && availableTimeInterval.amount == amount && availableTimeInterval.maxAmount == maxAmount;
    }

    @Override
    public String toString() {
        return "AvailableTimeInterval{" + "date=" + date + ", timeInterval=" + timeInterval + ", amount=" + amount + ", maxAmount=" + maxAmount + '}';
    }
}
