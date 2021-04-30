package server.model.domain.user;

public class PendingStatus extends VaccineStatus {
    @Override
    public void approve(Patient patient) {
        patient.setVaccineStatus(new ApprovedStatus());
    }
    
    @Override
    public void decline(Patient patient) {
        patient.setVaccineStatus(new NotApprovedStatus());
    }
    
    @Override
    public String toString() {
        return "Pending";
    }
}
