package server.model.domain.appointment;

public class CanceledAppointment extends Status {
    @Override
    public boolean equals(Object obj) {
        return obj instanceof CanceledAppointment;
    }
    
    @Override
    public String toString() {
        return "Cancelled";
    }
}
