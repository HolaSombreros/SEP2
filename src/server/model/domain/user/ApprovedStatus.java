package server.model.domain.user;

public class ApprovedStatus extends VaccineStatus {
    @Override
    public boolean equals(Object obj) {
        return obj instanceof ApprovedStatus;
    }
    
    @Override
    public String toString() {
        return "Approved";
    }
}
