package server.model.domain.appointment;

public class ResultGivenAppointment extends Status {
    @Override
    public boolean equals(Object obj) {
        return obj instanceof ResultGivenAppointment;
    }

    @Override
    public String toString() {
        return "Results given";
    }
}
