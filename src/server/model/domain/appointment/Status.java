package server.model.domain.appointment;

import java.io.Serializable;

public abstract class Status implements Serializable {
    public void cancel(Appointment appointment) {
        // overwritten in UpcomingStatus
    }
    
    public void setResult(Appointment appointment, Result result) {
        // overwritten in FinishedStatus
    }
    
    @Override
    public boolean equals(Object obj) {
        return obj instanceof Status;
    }
}
