package server.model.domain.appointment;

import java.io.Serializable;

public abstract class Status implements Serializable {
    public void cancel(Appointment appointment) {
        // overwritten in UpcomingStatus
    }
    
    public void setResult(Appointment appointment) {
        // overwritten in FinishedStatus
    }
    
    public abstract String toString();
}
