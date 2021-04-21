package server.model;

public class TestAppointment extends Appointment {
    public enum Result {
        NEGATIVE("Negative"),
        POSITIVE("Positive"),
        INCONCLUSIVE("Inconclusive"),
        NORESULTSAVAILABLE("No results available");
        
        private String result;
    
        Result(String result) {
            this.result = result;
        }
    
        @Override
        public String toString() {
            return result;
        }
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
