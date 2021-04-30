package server.model.domain.appointment;

public class FinishedStatus extends Status {
    @Override
    public void setResult(Appointment appointment) {
        if (appointment.getType() != Type.VACCINE) {
            appointment.setStatus(new ResultGivenStatus());
        }
    }
    
    @Override
    public String toString() {
        return "Finished";
    }
}