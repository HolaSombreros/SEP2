package server.model.domain;

public class Patient extends User {

    public enum VaccineStatus {

        NOTAPPLIED("Not Applied"),
        PENDING("Pending"),
        APPROVED("Approved"),
        DECLINED("Declined");

        private String type;

        VaccineStatus(String type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return type;
        }

        public static VaccineStatus fromString(String type){
            for (VaccineStatus status: VaccineStatus.values()){
                if(status.type.equals(type))
                    return status;

            }
            return null;
         }
    }

    private VaccineStatus validForVaccine;
    
    public Patient(String cpr, String password, String firstName, String middleName, String lastName, Address address, String phone, String email, VaccineStatus validForVaccine) {
        super(cpr, password, firstName, middleName, lastName, address, phone, email);
        setValidForVaccine(validForVaccine);
    }
    
    public Patient(String cpr, String password, String firstName, String lastName, Address address, String phone, String email, VaccineStatus validForVaccine) {
        this(cpr, password, firstName, null, lastName, address, phone, email, validForVaccine);
    }

    
    public VaccineStatus isValidForVaccine() {
        return validForVaccine;
    }
    
    public void setValidForVaccine(VaccineStatus validForVaccine) {
        this.validForVaccine = validForVaccine;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Patient)) {
            return false;
        }
        Patient patient = (Patient) obj;
        return super.equals(obj) && validForVaccine == patient.validForVaccine;
    }
    
    @Override
    public String toString() {
        return super.toString() + " | Vaccine Status: " + validForVaccine;
    }
}
