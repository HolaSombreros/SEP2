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
            while (!(appointment.getDate().isBefore(LocalDate.now()) || (appointment.getDate().isBefore(LocalDate.now()) && appointment.getTimeInterval().getTo().isBefore(LocalTime.now()))))
                Thread.sleep(1000);
            appointment.setStatus(new FinishedAppointment());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
