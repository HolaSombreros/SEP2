package server.model;

public class Patient extends User {
    private boolean validForVaccine;
    private AppointmentList appointments;
    
    public Patient(String cpr, String password, String firstName, String middleName, String lastName, Address address, String phone, String email, boolean validForVaccine) {
        super(cpr, password, firstName, middleName, lastName, address, phone, email);
        setValidForVaccine(validForVaccine);
        this.appointments = new AppointmentList();
    }

    public Patient(String cpr, String password, String firstName,String lastName, Address address, String phone, String email, boolean validForVaccine){
        this(cpr, password,firstName, null, lastName, address,phone, email, validForVaccine);
    }
    
    public boolean isValidForVaccine() {
        return validForVaccine;
    }
    
    public void setValidForVaccine(boolean validForVaccine) {
        this.validForVaccine = validForVaccine;
    }
    
    @Override
    public String getType() {
        return getClass().getName();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Patient)) {
            return false;
        }
        Patient patient = (Patient) obj;
        return super.equals(obj) && validForVaccine == patient.validForVaccine && appointments.equals(patient.appointments);
    }
    public AppointmentList getAppointments(){
        return appointments;
    }
    public void addAppointment(Appointment appointment){
        if(appointment != null){
            appointments.add(appointment);
        }
        else
            throw new IllegalArgumentException("Please create an appointment");
    }
    
    @Override
    public String toString() {
        return super.toString() + " | Approved for vaccine: " + (validForVaccine ? "Yes" : "No");
    }
}
