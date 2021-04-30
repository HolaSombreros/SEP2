package server.model.domain.appointment;

public class UpcomingStatus extends Status {
    private CountDown timer;
    
    public UpcomingStatus(Appointment appointment) {
        // TODO : Perhaps disable timer when patient logs off so its not running on the server. can then check and update when they log in
        timer = new CountDown(appointment);
        Thread thread = new Thread(timer);
        thread.start();
    }
    
    @Override
    public void cancel(Appointment appointment) {
        appointment.setStatus(new CancelledStatus());
    }
    
    @Override
    public boolean equals(Object obj) {
        return obj instanceof UpcomingStatus;
    }
    
    @Override
    public String toString() {
        return "Upcoming";
    }
}
