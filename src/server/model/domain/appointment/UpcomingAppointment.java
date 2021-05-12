package server.model.domain.appointment;

public class UpcomingAppointment extends Status {
    private CountDown timer;
    
    public UpcomingAppointment(Appointment appointment) {
        // TODO : Perhaps disable timer when patient logs off so its not running on the server. can then check and update when they log in
        timer = new CountDown(appointment);
        timer.start();
    }
    
    @Override
    public void cancel(Appointment appointment) {
        appointment.setStatus(new CancelledAppointment());
    }
    
    @Override
    public boolean equals(Object obj) {
        return obj instanceof UpcomingAppointment;
    }
    
    @Override
    public String toString() {
        return "Upcoming";
    }
}
