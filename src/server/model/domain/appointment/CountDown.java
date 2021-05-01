package server.model.domain.appointment;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

public class CountDown extends Thread implements Serializable {
    private Appointment appointment;
    
    public CountDown(Appointment appointment) {
        this.appointment = appointment;
    }
    
    @Override
    public void run() {
        try {
            while (!appointment.getDate().isBefore(LocalDate.now()) && !appointment.getTimeInterval().getTo().isBefore(LocalTime.now())) {
                System.out.println(LocalTime.now());
                System.out.println(appointment.getTimeInterval().getTo());
                Thread.sleep(1000);
            }
            appointment.setStatus(new FinishedStatus());
        }
        catch (InterruptedException e) {
        }
    }
}
