package server.model.domain.appointment;

import java.time.LocalDate;
import java.time.LocalTime;

public class UpcomingStatus extends Status {
    private Thread timer;
    
    public UpcomingStatus(Appointment appointment) {
        timer = new Thread(() -> {
            try {
                while (LocalDate.now().isBefore(appointment.getDate()) && LocalTime.now().isBefore(appointment.getTimeInterval().getTo())) {
                    Thread.sleep(1000);
                }
                appointment.setStatus(new FinishedStatus());
            }
            catch (InterruptedException e) {
            }
        });
        timer.start();
    }
    
    @Override
    public void cancel(Appointment appointment) {
        appointment.setStatus(new CancelledStatus());
    }
}
