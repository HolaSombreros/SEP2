package server.model;

import server.database.*;
import server.model.domain.appointment.*;
import server.model.domain.user.*;
import utility.observer.listener.GeneralListener;
import utility.observer.subject.PropertyChangeAction;
import utility.observer.subject.PropertyChangeProxy;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class ServerModelManager implements ServerModel {
    private UserList userList;
    private UserList patientList;
    private UserList nurseList;
    private UserList adminList;
    private UserList onlineList;
    private AppointmentTimeIntervalList appointmentTimeIntervalList;
    private ManagerFactory managerFactory;
    private PropertyChangeAction<User, Appointment> property;
    
    public ServerModelManager() {
        property = new PropertyChangeProxy<>(this);
        onlineList = new UserList();
        userList = new UserList();
        managerFactory = new ManagerFactory();
        loadUsers();
        loadTimeIntervals();
        loadAppointments();
        addDummyData();
    }
    
    private void loadUsers() {
        try {
            patientList = managerFactory.getUserManager().getAllPatients();
            nurseList = managerFactory.getUserManager().getAllNurses();
            adminList = managerFactory.getUserManager().getAllAdministrators();
            userList = managerFactory.getUserManager().getAllUsers();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void loadTimeIntervals() {
        //        from 8:00 -> 8:20  'til  19:00 -> 19:20 on current day
        //        for (int i = 8; i < 20; i++) {
        //            appointmentTimeIntervalList.add(new AppointmentTimeInterval(LocalDate.now(), new TimeInterval(LocalTime.of(i, 0), LocalTime.of(i, 20))));
        //        }
        
        // TODO - Load nurse schedules
    }
    
    private void loadAppointments() {
        try {
            appointmentTimeIntervalList = managerFactory.getAppointmentManager().getAllAppointments();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void addDummyData() {
        ArrayList<Address> addresses = new ArrayList<>();
        addresses.add(new Address("Sesame Street", "2", 8700, "Horsens"));
        addresses.add(new Address("Sesame Street", "7A", 8700, "Horsens"));
        addresses.add(new Address("Via Street", "25B", 8700, "Horsens"));
        UserList userList = new UserList();
        userList.add(new Patient("2003036532", "password", "Hello", null, "World", addresses.get(1), "12587463", "elmo@email.com", new ApprovedStatus()));
        userList.add(new Patient("2003045698", "password", "Maria", null, "Magdalena", addresses.get(2), "12587464", "holy@email.com", new PendingStatus()));
        userList.add(new Patient("3105026358", "password", "Elmo", null, "Popescu", addresses.get(1), "12587465", "popescu@email.com", new NotAppliedStatus()));
        userList.add(new Patient("2504012368", "password", "Vaseline", null, "Veselin", addresses.get(0), "12587466", "vaseline@email.com", new NotApprovedStatus()));
        userList.add(new Nurse("1302026584", "password", "Mikasa", null, "Ackerman", addresses.get(0), "12587467", "aot@email.com", "mikasa_nurse"));
        userList.add(new Administrator("1407026358", "password", "Nico", null, "Robin", addresses.get(0), "12569873", "nicoRobin@email.com", "nicoRobin_admin"));
        try {
            for (Address address : addresses)
                if (!managerFactory.getAddressManager().isAddress(address.getStreet(), address.getNumber(), address.getZipcode()))
                    managerFactory.getAddressManager().addAddress(address);
            for (User user : userList.getUsers())
                if (user instanceof Patient && !managerFactory.getPatientManager().isPatient(user)) {
                    managerFactory.getUserManager().addPerson(user);
                }
                else if (user instanceof Nurse && !managerFactory.getNurseManager().isNurse((Nurse) user)) {
                    managerFactory.getUserManager().addNurse((Nurse) user);
                }
                else if (user instanceof Administrator && !managerFactory.getAdministratorManager().isAdmin((Administrator) user)) {
                    managerFactory.getUserManager().addAdministrator((Administrator) user);
                }
            
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String generateRandomId() {
        return "AAAA";
    }
    
    @Override
    public synchronized User login(String cpr, String password) {
        User user = userList.getUserByCpr(cpr);
        if (user != null) {
            if (userList.getUserByCpr(cpr).getPassword().equals(password)) {
                if (onlineList.contains(cpr)) {
                    throw new IllegalStateException("That user is already logged in");
                }
                else {
                    onlineList.add(user);
                    return user;
                }
            }
            else {
                throw new IllegalArgumentException("That CPR/password combination does not match");
            }
        }
        else {
            throw new IllegalArgumentException("That CPR is not registered in the system");
        }
    }
    
    @Override
    public synchronized void register(String cpr, String password, String firstName, String middleName, String lastName, String phone, String email, String street, String number, int zip,
        String city) {
        if (!userList.contains(cpr)) {
            Address address = new Address(street, number, zip, city);
            User user = new Patient(cpr, password, firstName, middleName, lastName, address, phone, email, new NotAppliedStatus());
            userList.add(user);
            patientList.add(user);
            try {
                managerFactory.getUserManager().addPerson(user);
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else {
            throw new IllegalStateException("That CPR is already registered in the system");
        }
    }
    private void updateList(){
        try {
            patientList = managerFactory.getUserManager().getAllPatients();
            nurseList = managerFactory.getUserManager().getAllNurses();
            adminList = managerFactory.getUserManager().getAllAdministrators();
            userList = managerFactory.getUserManager().getAllUsers();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public synchronized UserList getUserList() {
        return userList;
    }
    
    @Override
    public synchronized UserList getPatientList() {
        return patientList;
    }
    
    @Override
    public synchronized UserList getNurseList() {
        return nurseList;
    }
    
    @Override
    public synchronized UserList getAdministratorList() {
        return adminList;
    }
    
    @Override
    public synchronized User editUserInformation(User user, String password, String firstName, String middleName, String lastName, String phone, String email, String street, String number, int zip) {
        User user2 = null;
        try {
            user2 = userList.getUserByCpr(user.getCpr());
            String city = managerFactory.getAddressManager().getCity(zip);
            user2.editUserInformation(password, firstName, middleName, lastName, new Address(street, number, zip, city), phone, email);
            managerFactory.getUserManager().updateUserInformation(user2, password, firstName, middleName, lastName, phone, email, street, number, zip);
            updateList();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return user2;
    }
    
    @Override
    public synchronized VaccineStatus applyForVaccination(Patient patient) {
        try {
            patient.getVaccineStatus().apply(patient);
            managerFactory.getPatientManager().setVaccineStatus(patient.getCpr(), patient.getVaccineStatus());
            updateList();
            return patient.getVaccineStatus();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    @Override
    public synchronized void addSchedule(Nurse nurse, Schedule schedule) {
        try {
            nurse = (Nurse) nurseList.getUserByCpr(nurse.getCpr());
            if (nurse.worksThatDay(schedule)) {
                nurse.editSchedule(schedule);
                managerFactory.getNurseScheduleManager().editNurseSchedule(nurse, schedule);
            }
            else {
                nurse.addSchedule(schedule);
                managerFactory.getNurseScheduleManager().addNurseSchedule(nurse, schedule);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public synchronized void removeSchedule(Nurse nurse, Schedule schedule) {
        try {
            nurse = (Nurse) nurseList.getUserByCpr(nurse.getCpr());
            nurse.removeSchedule(schedule);
            managerFactory.getNurseScheduleManager().removeNurseSchedule(nurse, schedule);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public synchronized Appointment addAppointment(LocalDate date, TimeInterval timeInterval, Type type, Patient patient) {
        Appointment appointment = null;
        try {
            // TODO: assign nurse automatically based on their schedule, somehow
            Nurse nurse = (Nurse) userList.getUserByCpr("1302026584");
            int id = managerFactory.getAppointmentManager().getNextId();
            switch (type) {
                case TEST:
                    appointment = new TestAppointment(id, date, timeInterval, type, patient, nurse);
                    break;
                case VACCINE:
                    appointment = new VaccineAppointment(id, date, timeInterval, type, patient, nurse);
                    break;
            }
            appointmentTimeIntervalList.add(appointment, date, timeInterval);
            managerFactory.getAppointmentManager().addAppointment(appointment);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return appointment;
    }
    
    @Override
    public synchronized AppointmentList getAppointmentsByUser(User user) {
        return appointmentTimeIntervalList.getAppointmentsByUser(user);
    }
    
    @Override
    public synchronized Appointment getAppointmentById(int id) {
        return appointmentTimeIntervalList.getAppointmentById(id);
    }
    
    @Override
    public synchronized TimeIntervalList getAvailableTimeIntervals(LocalDate date) {
        return appointmentTimeIntervalList.getAvailableTimeIntervals(date);
    }
    
    @Override
    public synchronized void cancelAppointment(int id) {
        Appointment appointment = appointmentTimeIntervalList.getAppointmentById(id);
        try {
            if (appointment.getStatus() instanceof UpcomingAppointment) {
                if (appointment.cancel()) {
                    managerFactory.getAppointmentManager().cancelStatus(id);
                }
            }
            else
                throw new IllegalStateException("You cannot cancel a finished or cancelled appointment");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public synchronized void rescheduleAppointment(int id, LocalDate date, TimeInterval timeInterval) {
        try {
            if (appointmentTimeIntervalList.getAppointmentById(id).getStatus() instanceof UpcomingAppointment) {
                appointmentTimeIntervalList.getAppointmentById(id).reschedule(date, timeInterval);
                managerFactory.getAppointmentManager().rescheduleAppointment(id, date, timeInterval);
            }
            else
                throw new IllegalStateException("You cannot reschedule a finished or cancelled appointment");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public synchronized void logout(User user) {
        if (userList.contains(user)) {
            if (onlineList.contains(user)) {
                onlineList.remove(user);
            }
            else {
                throw new IllegalStateException("That user is not logged in");
            }
        }
        else {
            throw new IllegalArgumentException("No such user found");
        }
    }
    
    @Override
    public synchronized Patient getPatient(String cpr) {
        if (patientList.contains(cpr))
            return (Patient) patientList.getUserByCpr(cpr);
        else
            return null;
    }
    
    @Override
    public synchronized void changeResult(int id, Result result) {
        try {
            ((TestAppointment) appointmentTimeIntervalList.getAppointmentById(id)).setResult(result);
            TestAppointment appointment = (TestAppointment) appointmentTimeIntervalList.getAppointmentById(id);
            managerFactory.getAppointmentManager().changeResult(appointment);
            updateList();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        
    }

    @Override
    public synchronized VaccineStatus updateVaccineStatus(Patient patient) {
        try{
            managerFactory.getPatientManager().setVaccineStatus(patient.getCpr(), patient.getVaccineStatus());
            updateList();
            return patient.getVaccineStatus();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override public void setRole(User user, String role) {
        switch (role) {
            case "Nurse":
                Nurse nurse = new Nurse(user.getCpr(), user.getPassword(), user.getFirstName(), user.getMiddleName(), user.getLastName(), user.getAddress(), user.getPhone(),
                    user.getEmail(), generateRandomId());
                nurseList.add(nurse);
                try {
                    managerFactory.getNurseManager().addNurse(nurse);
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "Administrator":
                Administrator administrator = new Administrator(user.getCpr(), user.getPassword(), user.getFirstName(), user.getMiddleName(), user.getLastName(), user.getAddress(),
                    user.getPhone(), user.getEmail(),generateRandomId());
                adminList.add(administrator);
                try {
                    managerFactory.getAdministratorManager().addAdministrator(administrator);
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override public void RemoveRole(User user) {
        switch (user.getClass().getSimpleName()) {
            case "Nurse":
                nurseList.remove(user);
                try {
                    managerFactory.getNurseManager().removeNurse((Nurse) user);
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "Administrator":
                adminList.remove(user);
                try {
                    managerFactory.getAdministratorManager().removeAdministrator((Administrator) user);
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public synchronized void close() {
        property.close();
    }
    
    @Override
    public boolean addListener(GeneralListener<User, Appointment> listener, String... propertyNames) {
        return property.addListener(listener, propertyNames);
    }
    
    @Override
    public boolean removeListener(GeneralListener<User, Appointment> listener, String... propertyNames) {
        return property.removeListener(listener, propertyNames);
    }
}
