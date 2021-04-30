package server.model.domain.appointment;

public class FinishedStatus extends Status {
    @Override
    public void giveResult(Appointment appointment) {
        if (appointment.getType() != Appointment.Type.VACCINE) {
            appointment.setStatus(new ResultGivenStatus());
        }
    }
}
