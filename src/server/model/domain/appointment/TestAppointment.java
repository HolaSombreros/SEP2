package server.model.domain.appointment;

import server.model.domain.user.Nurse;
import server.model.domain.user.Patient;

import java.time.LocalDate;

public class TestAppointment extends Appointment {
    private Result result;
    
    public TestAppointment(int id, LocalDate date, TimeInterval timeInterval, Type type, Patient patient, Nurse nurse) {
        super(id, date, timeInterval, type, patient, nurse);
        result = Result.NORESULTSAVAILABLE;
    }
    
    public Result getResult() {
        return result;
    }
    
    public void setResult(Result result) {
        if (getStatus() instanceof FinishedStatus) {
            this.result = result;
        }
        else {
            throw new IllegalStateException("You cannot change an unfinished appointment's results");
        }
    }

    public String toString(){
        return super.toString() + " " + result;
    }
}
