package server.model.domain.user;

public class NotAppliedStatus extends VaccineStatus
{
    @Override
    public void apply(Patient patient) {
        patient.setVaccineStatus(new PendingStatus());
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof NotAppliedStatus;
    }

    @Override
    public String toString() {
        return "Not Applied";
    }
}
