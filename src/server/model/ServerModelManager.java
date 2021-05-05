package server.model;

import server.database.*;
import server.model.domain.appointment.*;
import server.model.domain.user.*;
import utility.observer.listener.GeneralListener;
import utility.observer.subject.PropertyChangeAction;
import utility.observer.subject.PropertyChangeProxy;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
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
    
    // TODO - figure out a way to update statuses in the database based on current time
    
    public ServerModelManager() {
        property = new PropertyChangeProxy<>(this);
        onlineList = new UserList();
        managerFactory = new ManagerFactory();
        loadUsers();
        loadAppointments();
        addDummyData();
        addDummyTimeIntervals();
    }
    
    private void loadUsers() {
        try {
            userList = managerFactory.getUserManager().getAllUsers();
            patientList = managerFactory.getUserManager().getAllPatients();
            nurseList = managerFactory.getUserManager().getAllNurses();
            adminList = managerFactory.getUserManager().getAllAdministrators();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadAppointments() {
        // TODO : update appointment statuses where needed. isBefore()
        try {
            appointmentTimeIntervalList = managerFactory.getAppointmentManager().getAllAppointments();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void addDummyData() {
        ArrayList<Address> addresses = new ArrayList<>();
        addresses.add(new Address("Sesame Street","2",8700,"Horsens"));
        addresses.add(new Address("Sesame Street", "7A",8700,"Horsens"));
        addresses.add(new Address("Via Street","25B",8700,"Horsens"));
        VaccineStatus status = new NotApprovedStatus();
        UserList userList = new UserList();
        userList.add(new Patient("2003036532","password","Hello",null,"World",addresses.get(1),"12587463","elmo@email.com",status));
        userList.add(new Patient("2003045698","password","Maria",null,"Magdalena",addresses.get(2),"12587464","holy@email.com",status));
        userList.add(new Patient("3105026358","password","Elmo",null,"Popescu",addresses.get(1),"12587465","popescu@email.com",status));
        userList.add(new Patient("2504012368","password","Vaseline",null,"Veselin",addresses.get(0),"12587466","vaseline@email.com",status));
        userList.add(new Nurse("1302026584","password","Mikasa",null,"Ackerman",addresses.get(0),"12587467","aot@email.com","mikasa_nurse"));
        userList.add(new Administrator("1407026358","password","Nico",null,"Robin",addresses.get(0),"12569873","nicoRobin@email.com","nicoRobin_admin"));
        try {
           for (Address address : addresses)
               if (!managerFactory.getAddressManager().isAddress(address.getStreet(),address.getNumber(),address.getZipcode()))
                   managerFactory.getAddressManager().addAddress(address);
           for(User user: userList.getUsers())
               if( user instanceof Patient && !managerFactory.getPatientManager().isPatient(user)) {
                   managerFactory.getUserManager().addPerson(user);
               }
               else if(user instanceof Nurse && !managerFactory.getNurseManager().isNurse((Nurse) user)) {
                   managerFactory.getUserManager().addNurse(user);
               }
               else if (user instanceof Administrator && !managerFactory.getAdministratorManager().isAdmin((Administrator) user)){
                   managerFactory.getUserManager().addAdministrator(user);
               }

       }
       catch (SQLException e){
           e.printStackTrace();
       }
    }
    
    private void addDummyTimeIntervals() {
        // from 8:00 -> 8:20  'til  15:00 -> 15:20 on current day
        for (int i = 8; i < 16; i++) {
            appointmentTimeIntervalList.add(new AppointmentTimeInterval(LocalDate.now(), new TimeInterval(LocalTime.of(i, 0), LocalTime.of(i, 20))));
        }
    }
    @Override
    public synchronized User login(String cpr, String password) {
        User user = userList.getUserByCpr(cpr);
        if (userList.getUserByCpr(cpr).getPassword().equals(password)){
            if (onlineList.contains(cpr)) {
                throw new IllegalStateException("That user is already logged in");
            }
            else {
                onlineList.add(user);
                return user;
            }
        }
        else
            throw new IllegalArgumentException("That username/password combination does not match");
    }
    
    @Override
    public synchronized void register(String cpr, String password, String firstName, String middleName, String lastName, String phone, String email, String street, String number, int zip,
        String city) {
        if (!userList.contains(cpr)) {
            Address address = new Address(street, number, zip, city);
            User user = new Patient(cpr, password, firstName, middleName, lastName, address, phone, email, new NotApprovedStatus());
            userList.add(user);
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
    
    @Override
    public synchronized UserList getUserList() {
        return userList;
    }

    @Override
    public synchronized UserList getPatientList()
    {
        return patientList;
    }

    @Override
    public synchronized UserList getNurseList()
    {
        return nurseList;
    }

    @Override
    public synchronized UserList getAdministratorList()
    {
        return adminList;
    }

    @Override
    public synchronized User editUserInformation(User user, String password, String firstName, String middleName, String lastName, String phone, String email, String street, String number, int zip)
    {
        User user2 = null;
        try{
            user2 = userList.getUserByCpr(user.getCpr());
            String city = managerFactory.getAddressManager().getCity(zip);
            user2.editUserInformation(password, firstName, middleName, lastName, new Address(street, number, zip,city), phone, email);
            managerFactory.getUserManager().updateUserInformation(user2, password, firstName, middleName, lastName, phone, email, street, number, zip);
       }
        catch(SQLException e){
           e.printStackTrace();
        }
        return user2;
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
                    return new TestAppointment(id, date, timeInterval, type, patient, nurse);
                case VACCINE:
                    return new VaccineAppointment(id, date, timeInterval, type, patient, nurse);
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
