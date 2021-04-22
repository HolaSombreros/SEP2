package server.model;

import utility.observer.listener.GeneralListener;
import utility.observer.subject.PropertyChangeAction;
import utility.observer.subject.PropertyChangeProxy;

public class ServerModelManager implements ServerModel {
    private UserList userList;
    private UserList onlineList;
    private AppointmentTimeList appointmentTimeList;
    
    private PropertyChangeAction<User, Appointment> property;
    
    public ServerModelManager() {
        property = new PropertyChangeProxy<>(this);
        userList = new UserList();
        onlineList = new UserList();
        appointmentTimeList = new AppointmentTimeList();
        addDummyData();
        addDummyTimeIntervals();
    }
    
    private void addDummyData() {
        userList.addUser(new Patient("1204560000", "testpassword", "Test", "Person", new Address("TestStreet", "0", 8700, "Horsens"), "12345678", "test@email.com", false));
        userList.addUser(new Nurse("1205561111", "testpassword", "Test", "Person", new Address("TestStreet", "0", 8700, "Horsens"), "12345678", "test@email.com", "emp1"));
        userList.addUser(new Administrator("1211562222", "testpassword", "Test", "Person", new Address("TestStreet", "0", 8700, "Horsens"), "12345678", "test@email.com", "emp2"));
    }
    
    private void addDummyTimeIntervals() {
        // from 8:00 -> 8:20  'til  15:00 -> 15:20 on current day
        for (int i = 8; i < 16; i++) {
            appointmentTimeList.add(new AppointmentTimeFrame(Date.today(), new TimeInterval(new Time(i, 0), new Time(i, 20))));
        }
        appointmentTimeList.add(new AppointmentTimeFrame(new Date(22, 4, 2021), new TimeInterval(new Time(8, 0), new Time(12, 20))));
    }
    
    @Override
    public User login(String cpr, String password) {
        // validate cpr and password first?
        if (userList.contains(cpr)) {
            User user = userList.getUserByCpr(cpr);
            if (user.getPassword().equals(password)) {
                if (!onlineList.contains(user)) {
                    onlineList.addUser(user);
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
        else {
            throw new IllegalStateException("That user does not exist");
        }
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
    public void addAppointment(Date date, TimeInterval timeInterval, Appointment.Type type, Patient patient) {
        Appointment appointment = null;

        // TODO: assign nurse automatically based on their schedule, somehow
        switch (type) {
            case TEST:
                appointment = new TestAppointment(date, timeInterval, type, patient, (Nurse) userList.getUserByCpr("1205561111"));
                break;
            case VACCINE:
                appointment = new VaccineAppointment(date, timeInterval, type, patient, (Nurse) userList.getUserByCpr("1205561111"));
                break;
            default:
                throw new IllegalStateException("Appointment type '" + type + "' is invalid");
        }
        appointmentTimeList.add(appointment, date, timeInterval);
    }
    
    @Override
    public AppointmentList getAppointmentsByUser(User user) {
        return appointmentTimeList.getAppointsByUser(user);
    }
    
    @Override
    public Appointment getAppointmentById(int id) {
        return appointmentTimeList.getAppointmentById(id);
    }
    
    @Override
    public TimeIntervalList getAvailableTimeIntervals(Date date) {
        return appointmentTimeList.getAvailableTimeIntervals(date);
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
