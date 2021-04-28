package server.database;

public class ManagerFactory {
    private PatientManager patientManager;
    private NurseManager nurseManager;
    private AppointmentManager appointmentManager ;
    private AddressManager addressManager ;
    private AdministratorManager administratorManager;

    public ManagerFactory() {
        patientManager = new PatientManager();
        administratorManager = new AdministratorManager();
        nurseManager = new NurseManager();
        appointmentManager = new AppointmentManager();
        addressManager = new AddressManager();
    }
    public PatientManager getPatientManager() {
        return patientManager;
    }

    public NurseManager getNurseManager() {
        return nurseManager;
    }

    public AppointmentManager getAppointmentManager() {
        return appointmentManager;
    }

    public AddressManager getAddressManager() {
        return addressManager;
    }

    public AdministratorManager getAdministratorManager() {
        return administratorManager;
    }
}
