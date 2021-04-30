package server.model.domain;

import java.time.LocalDate;

public class TestAppointment extends Appointment {

    private Result result;
    
    public TestAppointment(LocalDate date, TimeInterval timeInterval, Type type, Patient patient, Nurse nurse) {
        super(date, timeInterval, type, patient, nurse);
        result = Result.NORESULTSAVAILABLE;
    }
    
    public Result getResult() {
        return result;
    }
    
    public void setResult(Result result) {
        this.result = result;
    }

    public String toString(){
        return super.toString() + " " + result;
    }
}
