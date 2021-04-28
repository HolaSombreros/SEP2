package server.model;

import server.database.*;
import server.model.domain.*;
import utility.observer.listener.GeneralListener;
import utility.observer.subject.PropertyChangeAction;
import utility.observer.subject.PropertyChangeProxy;

import java.sql.SQLException;
import java.util.ArrayList;


public class ServerModelManager implements ServerModel {
    private UserList userList;
    private UserList onlineList;
    private AppointmentTimeList appointmentTimeList;
    private ManagerFactory managerFactory;
    
    private PropertyChangeAction<User, Appointment> property;
    
    public ServerModelManager() {
        property = new PropertyChangeProxy<>(this);
        userList = new UserList();
        onlineList = new UserList();
        appointmentTimeList = new AppointmentTimeList();
        managerFactory = new ManagerFactory();
        addDummyData();
        addDummyTimeIntervals();
    }
    
    private void addDummyData() {
        ArrayList<Address> addresses = new ArrayList<>();
        addresses.add(new Address("Sesame Street","2",8700,"Horsens"));
        addresses.add(new Address("Sesame Street", "7A",8700,"Horsens"));
        addresses.add(new Address("Via Street","25B",8700,"Horsens"));
        userList.addUser(new Patient("2003036532","password","Hello",null,"World",addresses.get(1),"12587463","elmo@email.com", Patient.VaccineStatus.NOTAPPLIED));
        userList.addUser(new Patient("2003045698","password","Maria",null,"Magdalena",addresses.get(2),"12587464","holy@email.com",Patient.VaccineStatus.NOTAPPLIED));
        userList.addUser(new Patient("3105026358","password","Elmo",null,"Popescu",addresses.get(1),"12587465","popescu@email.com",Patient.VaccineStatus.NOTAPPLIED));
        userList.addUser(new Patient("2504012368","password","Vaseline",null,"Veselin",addresses.get(0),"12587466","vaseline@email.com",Patient.VaccineStatus.NOTAPPLIED));
        userList.addUser(new Nurse("1302026584","password","Mikasa",null,"Ackerman",addresses.get(0),"12587467","aot@email.com","mikasa_nurse"));
        userList.addUser(new Administrator("1407026358","password","Nico",null,"Robin",addresses.get(0),"12569873","nicoRobin@email.com","nicoRobin_admin"));
        try {
           for (Address address : addresses)
               if (!managerFactory.getAddressManager().isAddress(address.getStreet(),address.getNumber(),address.getZipcode()))
                   managerFactory.getAddressManager().addAddress(address);
           for(User user: userList.getUsersList())
               if( user instanceof Patient &&!managerFactory.getPatientManager().isPatient(user) )
                   managerFactory.getPatientManager().addPatient(user);
               else if(user instanceof Nurse && !managerFactory.getNurseManager().isNurse((Nurse) user))
                   managerFactory.getNurseManager().addNurse((Nurse)user);
               else if (user instanceof Administrator && !managerFactory.getAdministratorManager().isAdmin((Administrator) user))
                   managerFactory.getAdministratorManager().addAdministrator((Administrator) user);

       }
       catch (SQLException e){
           e.printStackTrace();
       }
    }
    
    private void addDummyTimeIntervals() {
        // from 8:00 -> 8:20  'til  15:00 -> 15:20 on current day
        for (int i = 8; i < 16; i++) {
            appointmentTimeList.add(new AppointmentTimeFrame(Date.today(), new TimeInterval(new Time(i, 0), new Time(i, 20))));
        }
        appointmentTimeList.add(new AppointmentTimeFrame(new Date(22, 4, 2021), new TimeInterval(new Time(8, 0), new Time(12, 20))));
    }
    
    @Override
    public synchronized User login(String cpr, String password) {
        try{
            User user;
            if(managerFactory.getNurseManager().isNurse(cpr))
                 user = managerFactory.getNurseManager().getNurseByCPR(cpr);
            else if(managerFactory.getAdministratorManager().isAdmin(cpr))
                user = managerFactory.getAdministratorManager().getAdministratorByCpr(cpr);
            else
                user = managerFactory.getPatientManager().getPatientByCpr(cpr);
            if(managerFactory.getPatientManager().getPassword(cpr).equals(password)){
                if (!onlineList.contains(user)) {
                    onlineList.addUser(user);
                    return user;
                }
                else
                    throw new IllegalStateException("That user is already signed in");
            }
            else
                throw new IllegalArgumentException("That username/password combination does not match");
        }
        catch (SQLException | IllegalStateException e){
            System.out.println("aci");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public synchronized void register(String cpr, String password, String firstName, String middleName, String lastName, String phone, String email, String street, String number, int zip, String city) {

        if (!userList.contains(cpr)) {
            
            // validate the data
            Address address = new Address(street, number, zip, city);
            User user = new Patient(cpr, password, firstName, middleName, lastName, address, phone, email, Patient.VaccineStatus.NOTAPPLIED);
            userList.addUser(user);
            try {
               managerFactory.getPatientManager().addPatient(user);
            }
            catch (SQLException e){
                e.printStackTrace();
            }
        }
        else {
            throw new IllegalStateException("That CPR is already registered in the system");
        }
    }

    @Override
    public UserList getUserList()
    {
        return userList;
    }
    
    @Override
    public synchronized void addAppointment(Date date, TimeInterval timeInterval, Appointment.Type type, Patient patient) {
        Appointment appointment;

        // TODO: assign nurse automatically based on their schedule, somehow
        switch (type) {
            case TEST:
                appointment = new TestAppointment(date, timeInterval, type, patient, (Nurse) userList.getUserByCpr("1205561111"));
                try {
                    managerFactory.getAppointmentManager().addAppointment(appointment);
                }
                catch (SQLException e){
                    e.printStackTrace();
                }
                break;
            case VACCINE:
                appointment = new VaccineAppointment(date, timeInterval, type, patient, (Nurse) userList.getUserByCpr("1205561111"));
                try {
                    managerFactory.getAppointmentManager().addAppointment(appointment);
                }
                catch (SQLException e){
                    e.printStackTrace();
                }
                break;
            default:
                throw new IllegalStateException("Appointment type '" + type + "' is invalid");
        }
        appointmentTimeList.add(appointment, date, timeInterval);
    }
    
    @Override
    public synchronized AppointmentList getAppointmentsByUser(User user) {
        return appointmentTimeList.getAppointmentsByUser(user);
    }
    
    @Override
    public synchronized Appointment getAppointmentById(int id) {
        return appointmentTimeList.getAppointmentById(id);
    }
    
    @Override
    public synchronized TimeIntervalList getAvailableTimeIntervals(Date date) {
        return appointmentTimeList.getAvailableTimeIntervals(date);
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
