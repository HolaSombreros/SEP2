package server.model.domain.user;

public class NotApprovedStatus extends VaccineStatus {
    @Override
    public void apply(Patient patient) {
        patient.setVaccineStatus(new PendingStatus());
    }
    
    @Override
    public String toString() {
        return "Not Approved";
    }
}
