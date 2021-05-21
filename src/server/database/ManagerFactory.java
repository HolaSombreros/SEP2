package server.database;

public class ManagerFactory {
    private PatientManager patientManager;
    private NurseManager nurseManager;
    private AppointmentManager appointmentManager ;
    private AddressManager addressManager ;
    private AdministratorManager administratorManager;
    private UserManager userManager;
    private NurseScheduleManager nurseScheduleManager;
    private FAQManager faqManager;
    private TimeIntervalManager timeIntervalManager;

    public ManagerFactory() {
        patientManager = new PatientManager();
        administratorManager = new AdministratorManager();
        nurseManager = new NurseManager();
        addressManager = new AddressManager();
        userManager = new UserManager(addressManager, patientManager, nurseManager, administratorManager);
        appointmentManager = new AppointmentManager(userManager);
        nurseScheduleManager = new NurseScheduleManager();
        faqManager = new FAQManager();
        timeIntervalManager = new TimeIntervalManager();
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

    public UserManager getUserManager() {
        return userManager;
    }

    public NurseScheduleManager getNurseScheduleManager() {
        return nurseScheduleManager;
    }
    
    public FAQManager getFAQManager() {
        return faqManager;
    }
    
    public TimeIntervalManager getTimeIntervalManager() {
        return timeIntervalManager;
    }
}
