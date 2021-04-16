package server.model;

public class TestAppointment extends Appointment {
    public enum Result {
        NEGATIVE,
        POSITIVE,
        INCONCLUSIVE,
        NORESULTSAVAILABLE;
    }
    
    private Result result;
    
    public TestAppointment(Date date, TimeInterval timeInterval, Type type, User patient) {
        super(date, timeInterval, type, patient);
        result = Result.NORESULTSAVAILABLE;
    }
    
    public Result getResult() {
        return result;
    }
    
    public void setResult(Result result) {
        this.result = result;
    }
}
