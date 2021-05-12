package server.model.domain.appointment;

public class CancelledAppointment extends Status {
    @Override
    public boolean equals(Object obj) {
        return obj instanceof CancelledAppointment;
    }
    
    @Override
    public String toString() {
        return "Cancelled";
    }
}
