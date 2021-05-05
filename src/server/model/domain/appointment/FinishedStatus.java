package server.model.domain.appointment;

public class FinishedStatus extends Status {
    @Override
    public void setResult(Appointment appointment, Result result) {
        if (appointment.getType() == Type.TEST) {
            appointment.setStatus(new ResultGivenStatus());
            ((TestAppointment) appointment).setResult(result);
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
