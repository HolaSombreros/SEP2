package server.model;

import server.database.DatabaseManager;
import server.database.PatientManager;
import utility.observer.listener.GeneralListener;
import utility.observer.subject.PropertyChangeAction;
import utility.observer.subject.PropertyChangeProxy;

import java.sql.SQLException;
import java.util.List;


public class ServerModelManager implements ServerModel {
    private UserList userList;
    private UserList onlineList;
    private AppointmentTimeList appointmentTimeList;
    private PatientManager patientManager;
    
    private PropertyChangeAction<User, Appointment> property;
    
    public ServerModelManager() {
        property = new PropertyChangeProxy<>(this);
        userList = new UserList();
        onlineList = new UserList();
        appointmentTimeList = new AppointmentTimeList();
        patientManager = new PatientManager();
        addDummyData();
        addDummyTimeIntervals();
    }
    
    private void addDummyData() {
        userList.addUser(new Patient("1204560000", "testpassword", "Test", "Person", new Address("TestStreet", "0", 8700, "Horsens"), "12345678", "test@email.com", false));
        userList.addUser(new Nurse("1205561111", "testpassword", "Test", "Person", new Address("TestStreet", "0", 8700, "Horsens"), "12345678", "test@email.com", "emp1"));
        userList.addUser(new Administrator("1211562222", "testpassword", "Test", "Person", new Address("TestStreet", "0", 8700, "Horsens"), "12345678", "test@email.com", "emp2"));
    }
    
    private void addDummyTimeIntervals() {
        // from 8:00 -> 8:20  'til  15:00 -> 15:20
        for (int i = 8; i < 16; i++) {
            appointmentTimeList.add(new AppointmentList(new Date(), new TimeInterval(new Time(i, 0), new Time(i, 20))));
        }
    }
    
    @Override
    public User login(String cpr, String password)
    {
        try {
            User user = patientManager.getPatientByCpr(cpr);
            if (user.getPassword().equals(password)){
                System.out.println("Logged in as a: " + user.getCpr());
                return user;
            }
            else {
                throw new IllegalStateException("That user does not exist");
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        /*for(User user : userList.getUsersList()){
            if(user.getCpr().equals(cpr)){
                if(user.getPassword().equals(password)){
                    System.out.println("Logged in as a: " + user.getCpr());
=======
    public User login(String cpr, String password) {
        // validate cpr and password first?
        if (userList.contains(cpr)) {
            User user = userList.getUserByCpr(cpr);
            if (user.getPassword().equals(password)) {
                if (!onlineList.contains(user)) {
                    onlineList.addUser(user);
>>>>>>> branch-2
                    return user;
                }
                else {
                    throw new IllegalStateException("That user is already signed in");
                }
            }
            else {
                throw new IllegalArgumentException("That username/password combination does not match");
            }
        }
<<<<<<< HEAD

         */
       return null;

    }


        @Override
    public void logout(User user) {
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
    public void register(String cpr, String password, String firstName, String middleName, String lastName, String phone, String email, String street, String number, int zip, String city) {
        if (!userList.contains(cpr)) {
            
            // validate the data
            Address address = new Address(street, number, zip, city);
            User user = new Patient(cpr, password, firstName, middleName, lastName, address, phone, email, false);
            userList.addUser(user);
        }
        else {
            throw new IllegalStateException("That CPR is already registered in the system");
        }
    }


    @Override
    public void addAppointment(Date date, TimeInterval timeInterval, Appointment.Type type, User patient,User nurse) {
        // not sure how to do this in a way without using instanceof to verify...?
        if (patient instanceof Patient) {
            Appointment appointment = null;
    
            // validate the data
            switch (type) {
                case TEST:
                    appointment = new TestAppointment(date, timeInterval, type, patient,nurse);
                    break;
                case VACCINE:
                    appointment = new VaccineAppointment(date, timeInterval, type, patient,nurse);
                    break;
                default:
                    throw new IllegalStateException("Appointment type '" + type + "' is invalid");
            }
            
            appointmentTimeList.add(appointment, timeInterval);
        }
        else {
            throw new IllegalStateException("Please log in as a patient and try again");
        }
    }
    
    @Override
    public AppointmentTimeList getAppointmentsByUser(User user) {
        if (user.getType().equals("Patient")) {
            return appointmentTimeList.getAppointsByUser(user);
        }
        return null;
    }
    
    @Override
    public Appointment getAppointmentById(int id) {
        //return appointmentTimeList.getAppointmentById(id);
        return null;
    }
    
    @Override
    public TimeIntervalList getAvailableTimeIntervals() {
        //return appointmentTimeList.getAvailableTimeIntervals();
        return null;
    }
    
    @Override
    public void close() {
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
