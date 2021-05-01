package server.model.domain.appointment;

public class CancelledStatus extends Status {
    @Override
    public boolean equals(Object obj) {
        return obj instanceof CancelledStatus;
    }
    
    @Override
    public String toString() {
        return "Cancelled";
    }
}
