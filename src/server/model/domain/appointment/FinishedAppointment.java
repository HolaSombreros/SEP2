package server.model.domain.appointment;

public class FinishedAppointment extends Status {
    @Override
    public void setResult(Appointment appointment, Result result) {
        if (appointment.getType() == Type.TEST) {
            appointment.setStatus(new ResultGivenAppointment());
            ((TestAppointment) appointment).setResult(result);
        }
    }
    
    @Override
    public boolean equals(Object obj) {
        return obj instanceof FinishedAppointment;
    }
    
    @Override
    public String toString() {
        return "Finished";
    }
}
