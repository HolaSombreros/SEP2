package server.model.domain.appointment;

public class ResultGivenStatus extends Status {
    @Override
    public boolean equals(Object obj) {
        return obj instanceof ResultGivenStatus;
    }
    
    @Override
    public String toString() {
        return "Results given";
    }
}
