package server.database;

import server.model.domain.User;

import java.sql.SQLException;

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

    public User getUser(String cpr)throws SQLException {
        User user = null;
        if(getNurseManager().isNurse(cpr))
            user = getNurseManager().getNurseByCPR(cpr);
        else if(getAdministratorManager().isAdmin(cpr))
            user = getAdministratorManager().getAdministratorByCpr(cpr);
        else
            user = getPatientManager().getPatientByCpr(cpr);
        return user;
    }
}
