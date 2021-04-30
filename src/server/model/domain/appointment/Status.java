package server.model.domain.appointment;

public abstract class Status {
    public void cancel(Appointment appointment) {
        // overwritten in UpcomingStatus
    }
    
    public void setResult(Appointment appointment) {
        // overwritten in FinishedStatus
    }
    
    public String status() {
        return getClass().getSimpleName();
    }
}
