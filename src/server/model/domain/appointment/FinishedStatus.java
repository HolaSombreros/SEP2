package server.model.domain.appointment;

public class FinishedStatus extends Status {
    @Override
    public void setResult(Appointment appointment) {
        if (appointment.getType() != Type.VACCINE) {
            appointment.setStatus(new ResultGivenStatus());
        }
    }
    
    @Override
    public boolean equals(Object obj) {
        return obj instanceof FinishedStatus;
    }
    
    @Override
    public String toString() {
        return "Finished";
    }
}
