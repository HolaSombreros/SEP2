package server.model;

import server.database.*;
import server.model.domain.appointment.*;
import server.model.domain.chat.ChatLog;
import server.model.domain.chat.Message;
import server.model.domain.chat.UnreadStatus;
import server.model.domain.faq.Category;
import server.model.domain.faq.FAQ;
import server.model.domain.faq.FAQList;
import server.model.domain.user.*;
import server.model.validator.AppointmentValidator;
import server.model.validator.FAQValidator;
import server.model.validator.MessageValidator;
import utility.observer.listener.GeneralListener;
import utility.observer.listener.LocalListener;
import utility.observer.subject.PropertyChangeAction;
import utility.observer.subject.PropertyChangeProxy;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ServerModelManager implements ServerModel
{
    private UserList userList;
    private UserList onlineList;
    private FAQList faqList;
    private TimeIntervalList timeIntervalList;
    private AvailableTimeIntervalList availableTimeIntervalList;
    private AppointmentList appointmentList;

    private ShiftList shiftList;
    private ScheduleList scheduleList;
    private ManagerFactory managerFactory;
    private PropertyChangeAction<Object, Object> property;

    public ServerModelManager() throws RemoteException
    {
        property = new PropertyChangeProxy<>(this);
        onlineList = new UserList();
        userList = new UserList();
        managerFactory = new ManagerFactory();

        appointmentList = new AppointmentList();
        availableTimeIntervalList = new AvailableTimeIntervalList();

        doDummyStuff();
    }

    private void doDummyStuff() throws RemoteException {
        addDummyUsers();
        loadUsers();
        
        addShifts();
        loadShift();
        
        addTimeIntervals();
        loadTimeIntervals();

        loadSchedules();
        loadAppointments();

        loadFAQs();
       // addDummyFAQS();

//        PLEASE PUT THIS AFTER LOADING THE SCHEDULES AND APPOINTMENTS IF YOU WANNA MOVE IT
        loadAvailableTimeIntervals();
    }

    private void loadUsers() throws RemoteException
    {
        try {
            userList = managerFactory.getUserManager().getAllUsers();
            for(User user : userList.getUsers())
                if(user instanceof Patient)
                    ((Patient) user).setChatLog(managerFactory.getChatManager().getMessageByPatient((Patient)user));
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }
    }

    private void addShifts() throws RemoteException
    {
        try {
            managerFactory.getNurseScheduleManager().addShift(LocalTime.of(8,0),LocalTime.of(14,0));
            managerFactory.getNurseScheduleManager().addShift(LocalTime.of(14,0),LocalTime.of(20,0));
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }
    }

    private void loadShift() throws RemoteException
    {
        try {
            shiftList = managerFactory.getNurseScheduleManager().getAllShifts();
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }
    }

    private void addTimeIntervals() throws RemoteException {
        try
        {
          for (int i = 8; i < 21; i++) {
            managerFactory.getAppointmentManager().addTimeInterval(LocalTime.of(i, 0), LocalTime.of(i, 20));
            managerFactory.getAppointmentManager().addTimeInterval(LocalTime.of(i, 20), LocalTime.of(i, 40));
            managerFactory.getAppointmentManager().addTimeInterval(LocalTime.of(i, 40), LocalTime.of(i+1, 0));
          }
        }
        catch (SQLException e) {
            throw new RemoteException(e.getMessage());
        }
    }

    private void loadTimeIntervals() {
        try {
            timeIntervalList = managerFactory.getAppointmentManager().getTimeIntervals();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    private void loadAppointments() throws RemoteException
    {
        try {
            appointmentList = managerFactory.getAppointmentManager().getAllAppointments();
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }
    }

    private void addDummyFAQS() throws RemoteException {
        addFAQ("Can I book a test appointment for the same day?", "Yes, but you cannot book 2 appointments for the same time in the same day", Category.GENERAL,(Administrator)userList.getAdminList().getUsers().get(0));
        addFAQ("Am I allowed to get vaccinated if I currently have Covid-19?", "No, the vaccine takes place only if you are currently tested negative for Covid-19", Category.VACCINE, (Administrator)userList.getAdminList().getUsers().get(0));
        addFAQ("When will I receive the test result?", "It takes approximately 48 hours to provide a result. If you do not get the result in this time, you are encouraged to make another test appointment", Category.TEST, (Administrator)userList.getAdminList().getUsers().get(0));
        addFAQ("Why can't I register another account with the same CPR?", "Every person must have a single account with unique data", Category. GENERAL, (Administrator)userList.getAdminList().getUsers().get(0));
        addFAQ("How many times should I get vaccinated?", "Depends on the type of vaccine, if it is Pfizer there are 2 doses, Jonson&Jonson needs one dose", Category.VACCINE, (Administrator)userList.getAdminList().getUsers().get(0));
        addFAQ("When should I get tested for Covid-19", "In general, people with symptoms are getting tested, people who have taken part in activities that put them at higher risk for COVID-19 because they cannot physically distance as needed to avoid exposure such as travel, attending large social or mass gatherings, or being in crowded or poorly-ventilated indoor settings, people who have been asked or referred to get tested by their healthcare provider, or state, tribal, localexternal icon, or territorial health department", Category.TEST,(Administrator)userList.getAdminList().getUsers().get(0));
        addFAQ("What does my test mean?", "Positive = You are infected with Coronavirus, Negative = You are not infected with Coronavirus, Inconclusive = The test does not provide any certainty whether it is positive or negative",Category.TEST, (Administrator)userList.getAdminList().getUsers().get(0));
        addFAQ("What are the symptoms and complications that Covid-19 can cause?", "People with COVID-19 have reported a wide range of symptoms – from mild symptoms to severe illness. Symptoms may appear 2-14 days after exposure to the virus. If you have fever, cough, or other symptoms, you might have COVID-19", Category.GENERAL,(Administrator)userList.getAdminList().getUsers().get(0));
        addFAQ("Is it possible to have flu and Covid-19 at the same time? ","Yes. It is possible to test positive for flu (as well as other respiratory infections) and COVID-19 at the same time. Because some of the symptoms of flu and COVID-19 are similar, it may be hard to tell the difference between them based on symptoms alone. Testing may be needed to help confirm a diagnosis. The best way to prevent seasonal flu is to get vaccinated every year. Flu vaccines will not prevent COVID-19, but they will reduce your chances of getting flu", Category.GENERAL, (Administrator)userList.getAdminList().getUsers().get(0));
        addFAQ("If I am pregnant, can I get a Covid-19 vaccine?", "Yes, if you are pregnant, you can receive a COVID-19 vaccine. You might want to have a conversation with your healthcare provider to help you decide whether to get vaccinated. ", Category.VACCINE,(Administrator)userList.getAdminList().getUsers().get(0));
        addFAQ("What  is considered to be a close contact with someone who has the virus?", "For COVID-19, a close contact is anyone who was within 6 feet of an infected person for a total of 15 minutes or more over a 24-hour period (for example, three individual 5-minute exposures for a total of 15 minutes). An infected person can spread COVID-19 starting from 2 days before they have any symptoms (or, if they are asymptomatic, 2 days before their specimen that tested positive was collected), until they meet the criteria for discontinuing home isolation.", Category.GENERAL,(Administrator)userList.getAdminList().getUsers().get(0));
        addFAQ("Can I get Covid-19 from my pets or other animals?", "Based on the available information to date, the risk of animals spreading COVID-19 to people is considered to be low.", Category.GENERAL,(Administrator)userList.getAdminList().getUsers().get(0));
        addFAQ("Who is at increased risk for developing severe illness from Covid-19?", "People at increased risk include:\n" +
                "\n" +
                "Older adults\n" +
                "People of all ages with certain underlying medical conditions\n" +
                "Pregnant and recently pregnant people are also at increased risk for severe illness from COVID-19.", Category.GENERAL,(Administrator)userList.getAdminList().getUsers().get(0));
    }

    private void loadFAQs() throws RemoteException
    {
        try {
            faqList = managerFactory.getFAQManager().getAllFAQs();
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }
    }

    private void loadSchedules(){
        try {
            shiftList = managerFactory.getNurseScheduleManager().getAllShifts();
            scheduleList = managerFactory.getNurseScheduleManager().getAllSchedules();
            for (User nurse:  userList.getNurseList().getUsers()) {
                ((Nurse) nurse).setScheduleList(managerFactory.getNurseScheduleManager().getAllSchedulesForNurse((Nurse) nurse));
                for (Schedule schedule : ((Nurse) nurse).getScheduleList().getSchedules())
                    if (!schedule.getDateTo().isBefore(LocalDate.now()))
                        addAvailableTimeIntervals(schedule);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addAvailableTimeIntervals(Schedule schedule) {
        int hourFrom = schedule.getShift().getTimeFrom().getHour();
        int hourTo = hourFrom + 6;
        for (int i = 0; i<7; i++) {
            if (!schedule.getDateFrom().plusDays(i).isBefore(LocalDate.now()))
            for (int j = hourFrom; j < hourTo; j++) {
                availableTimeIntervalList.add(new AvailableTimeInterval(schedule.getDateFrom().plusDays(i), timeIntervalList.get(LocalTime.of(j, 0), LocalTime.of(j, 20))));
                availableTimeIntervalList.add(new AvailableTimeInterval(schedule.getDateFrom().plusDays(i), timeIntervalList.get(LocalTime.of(j, 20), LocalTime.of(j, 40))));
                availableTimeIntervalList.add(new AvailableTimeInterval(schedule.getDateFrom().plusDays(i), timeIntervalList.get(LocalTime.of(j, 40), LocalTime.of(j + 1, 0))));
            }
        }
    }

    private void removeAvailableTimeIntervals(Schedule schedule) {
        int hourFrom = schedule.getShift().getTimeFrom().getHour();
        int hourTo = hourFrom + 6;
        for (int i = 0; i < 7; i++) {
            if (!schedule.getDateFrom().plusDays(i).isBefore(LocalDate.now()))
                for (int j = hourFrom; j < hourTo; j++) {
                    availableTimeIntervalList.remove(new AvailableTimeInterval(schedule.getDateFrom().plusDays(i), timeIntervalList.get(LocalTime.of(j, 0), LocalTime.of(j, 20))));
                    availableTimeIntervalList.remove(new AvailableTimeInterval(schedule.getDateFrom().plusDays(i), timeIntervalList.get(LocalTime.of(j, 20), LocalTime.of(j, 40))));
                    availableTimeIntervalList.remove(new AvailableTimeInterval(schedule.getDateFrom().plusDays(i), timeIntervalList.get(LocalTime.of(j, 40), LocalTime.of(j + 1, 0))));
                }
        }
    }

    private void loadAvailableTimeIntervals() {
        for (AvailableTimeInterval interval : availableTimeIntervalList.getIntervals())
            for (Appointment appointment : appointmentList.getAppointments())
                if (interval.has(appointment))
                    interval.increaseAmount();
    }

    private AppointmentList getNurseUpcomingAppointments(Nurse nurse) {
        AppointmentList list = new AppointmentList();
        for (Appointment appointment : appointmentList.getAppointments())
            if (appointment.getNurse().equals(nurse) && appointment.getStatus() instanceof UpcomingAppointment)
                list.add(appointment);
        return list;
    }

    private Nurse getWorkingNurse(LocalDate date, TimeInterval timeInterval) {
        UserList list = new UserList();
        for (User user : userList.getNurseList().getUsers()) {
            Schedule schedule = ((Nurse) user).getScheduleByDate(date);
            if (schedule != null)
                if (schedule.getShift().hasTimeInterval(timeInterval)) {
                    if (getNumberOfAppointmentsForNurse(date, timeInterval, (Nurse) user) == 0)
                        return (Nurse) user;
                    else if (getNumberOfAppointmentsForNurse(date, timeInterval, (Nurse) user) == 1)
                        list.add(0, user);
                    else if (getNumberOfAppointmentsForNurse(date, timeInterval, (Nurse) user) == 2)
                        list.add(user);
                }
        }
        if (list.getUsers().size() == 0)
            throw new IllegalStateException("There is no nurse available");
        return (Nurse) list.getUsers().get(0);
    }

    private int getNumberOfAppointmentsForNurse(LocalDate date, TimeInterval timeInterval, Nurse nurse) {
        int counter = 0;
        for (Appointment appointment : appointmentList.getAppointments())
            if (appointment.getDate().equals(date) && appointment.getTimeInterval().equals(timeInterval) && appointment.getNurse().equals(nurse))
                counter++;
        return counter;
    }

    private String generateEmployeeId(String firstName, String middleName, String lastName) {
        final int MAX = 999;
        final int MIN = 100;

        String employeeId = "";
        if (middleName != null) {
            employeeId = firstName.charAt(0) + "" + middleName.charAt(0) + "" + lastName.charAt(0);
        }
        else {
            employeeId = firstName.substring(0, 2) + "" + lastName.substring(0, 2);
        }
        employeeId = employeeId.toUpperCase();

        // Add random number between 100 and 999 at the end
        boolean taken;
        int randomNumber;
        do {
            taken = false;
            randomNumber = (int) (Math.ceil(Math.random() * (MAX - MIN)) + MIN);
            for (User user : userList.getUsers()) {
                if (user instanceof Staff) {
                    Staff staff = ((Staff) user);
                    if (staff.getEmployeeId().equalsIgnoreCase(employeeId + randomNumber)) {
                        taken = true;
                        break;
                    }
                }
            }
        } while (taken);

        return employeeId + randomNumber;
    }

    private void addDummyUsers() throws RemoteException
    {
        ArrayList<Address> addresses = new ArrayList<>();
        addresses.add(new Address("Minvej", "2", 5520, "Someplace"));
        addresses.add(new Address("Sesame Street", "7A", 7500, "Holstebro"));
        addresses.add(new Address("Via Street", "25B", 2300, "Unknownville"));
        UserList userList = new UserList();

        userList.add(new Patient("2003036532", "password", "Hello", null, "World", addresses.get(1), "12587463", "elmo@email.com", new ApprovedStatus()));
        userList.add(new Patient("2003045698", "password", "Maria", null, "Magdalena", addresses.get(2), "12587464", "holy@email.com", new PendingStatus()));
        userList.add(new Patient("3105026358", "password", "Elmo", null, "Popescu", addresses.get(1), "12587465", "popescu@email.com", new NotAppliedStatus()));
        userList.add(new Patient("2504012368", "password", "Vaseline", null, "Veselin", addresses.get(0), "12587466", "vaseline@email.com", new NotApprovedStatus()));
        userList.add(new Nurse("1302026584", "password", "Mikasa", "Ackerman", addresses.get(0), "12587467", "aot@email.com", generateEmployeeId("Mikasa", null, "Ackerman")));
        userList.add(new Nurse("1805941234", "password", "Morten", "Frederik", "Hansen", new Address("Fabrikvej", "4", 8700, "Horsens"), "28800805", "morten.f.hansen@hotmail.com", generateEmployeeId("Morten", "Frederik", "Hansen")));
        userList.add(new Administrator("1407026358", "password", "Nico", "Robin", addresses.get(0), "12569873", "nicoRobin@email.com", generateEmployeeId("Nico", null, "Robin")));
        userList.add(new Administrator("2904010987", "password", "Adriana", null, "Grecea", new Address("Clujstreet", "319", 9150, "Romania"), "94735271", "adriana@grecea.net", generateEmployeeId("Adriana", null, "Grecea")));
        try {
            for (Address address : addresses)
                if (!managerFactory.getAddressManager().isAddress(address.getStreet(), address.getNumber(), address.getZipcode()))
                    managerFactory.getAddressManager().addAddress(address);
            for (User user : userList.getUsers())
                if (user instanceof Patient && !managerFactory.getPatientManager().isPatient(((Patient)user).getCpr())) {
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
            throw new RemoteException(e.getMessage());
        }
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
        String city) throws RemoteException
    {
        if (!userList.contains(cpr)) {
            Address address = new Address(street, number, zip, city);
            User user = new Patient(cpr, password, firstName, middleName, lastName, address, phone, email, new NotAppliedStatus());
            userList.add(user);
            property.firePropertyChange("NewPatient", user, null);
            try {
                managerFactory.getUserManager().addPerson(user);
                if (userList.size() == 0)
                    setRole(user, "Administrator");
            }
            catch (SQLException e) {
                e.printStackTrace();
                throw new RemoteException(e.getMessage());
            }
        }
        else {
            throw new IllegalStateException("That CPR is already registered in the system");
        }
    }
    private void updateList() throws RemoteException
    {
        try {
            userList = managerFactory.getUserManager().getAllUsers();
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }
    }

    @Override
    public synchronized UserList getUserList() {
        return userList;
    }

    @Override
    public synchronized UserList getPatientList() {
        return userList.getPatientList();
    }

    @Override
    public synchronized TimeIntervalList getTimeIntervalList() {
        return timeIntervalList;
    }

    @Override
    public synchronized UserList getNurseList() {
        return userList.getNurseList();
    }

    @Override
    public synchronized UserList getAdministratorList() {
        return userList.getAdminList();
    }

    public synchronized ShiftList getShiftList() {
        return shiftList;
    }

    public synchronized ScheduleList getScheduleList() {
        return scheduleList;
    }

    @Override
    public synchronized User editUserInformation(User user, String password, String firstName, String middleName, String lastName, String phone, String email, String street, String number, int zip) throws RemoteException
    {
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
            throw new RemoteException(e.getMessage());
        }
        return user2;
    }

    @Override
    public synchronized VaccineStatus applyForVaccination(Patient patient) throws RemoteException
    {
        try {
            patient.getVaccineStatus().apply(patient);
            managerFactory.getPatientManager().setVaccineStatus(patient.getCpr(), patient.getVaccineStatus());
            updateList();
            return patient.getVaccineStatus();
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());

        }
    }

    @Override
    public synchronized void editSchedule(Nurse nurse, LocalDate dateFrom, int shiftId) throws RemoteException
    {
        try {
            nurse = userList.getNurse(nurse.getCpr());
            if (nurse.worksThatWeek(dateFrom)) {
                if (nurse.getSchedule(dateFrom).getShift().getId() != shiftId || shiftId == 0) {
                    for (int i = 0; i < 7; i++)
                        for (Appointment appointment : getNurseUpcomingAppointments(nurse).getAppointments())
                            if (appointment.getDate().equals(dateFrom.plusDays(i)))
                                cancelAppointment(appointment.getId());
                    managerFactory.getNurseScheduleManager().removeNurseSchedule(nurse, nurse.getSchedule(dateFrom));
                    removeAvailableTimeIntervals(nurse.getSchedule(dateFrom));
                    nurse.removeSchedule(nurse.getSchedule(dateFrom));
                }
            }
            else if (shiftId != 0) {
                Shift shift = getShiftList().getById(shiftId);
                LocalDate dateTo = dateFrom.plusDays(6);
                Schedule schedule = managerFactory.getNurseScheduleManager().addSchedule(dateFrom, dateTo, shift);
                nurse.addSchedule(schedule);
                addAvailableTimeIntervals(schedule);
                managerFactory.getNurseScheduleManager().addNurseSchedule(nurse, schedule);
                }
            loadAvailableTimeIntervals();
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }
    }

    @Override
    public synchronized Appointment addAppointment(LocalDate date, TimeInterval timeInterval, Type type, Patient patient) throws RemoteException
    {
        Appointment appointment = null;
        try {
            Nurse nurse = getWorkingNurse(date, timeInterval);

            // Validate the appointment data - although makes more sense to do this in appointment actor, but we can't because the database generates its id
            AppointmentValidator.validateNewAppointment(date, timeInterval, type, patient, nurse);

            // Check if there is an appointment already at that time
            if (appointmentList.hasAppointment(patient, date, timeInterval))
                throw new IllegalStateException("You already have an appointment at the selected time");

            // Generate appointment from database
            appointment = managerFactory.getAppointmentManager().addAppointment(date, timeInterval, type, patient, nurse);

            // Check if the time is still available
            AvailableTimeInterval availableTimeInterval = availableTimeIntervalList.getByAvailableTimeInterval(new AvailableTimeInterval(date, timeInterval));
            if (availableTimeInterval == null || !availableTimeInterval.isAvailable()) {
                throw new IllegalStateException("The selected time is no longer available");
            }

            // Add appointment to local system cache
            appointmentList.add(appointment);

            // Update the AvailableTimeIntervalList
            availableTimeInterval.increaseAmount();
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }

        return appointment;
    }

    @Override
    public synchronized AppointmentList getAppointmentsByUser(User user) {
        return appointmentList.getAppointmentsByUser(user);
    }

    @Override
    public synchronized AppointmentList filterAppointmentsByNameAndCpr(String criteria, boolean showFinished, String appointmentType) {
        return appointmentList.filterAppointmentsByNameAndCpr(criteria, showFinished, appointmentType);
    }

    @Override
    public synchronized Appointment getAppointmentById(int id) {
        return appointmentList.getAppointmentById(id);
    }

    @Override
    public synchronized TimeIntervalList getAvailableTimeIntervals(LocalDate date) {
        return availableTimeIntervalList.getByDate(date);
    }

    @Override
    public synchronized void cancelAppointment(int id) throws RemoteException {
        Appointment appointment = appointmentList.getAppointmentById(id);
        try {
            if (appointment.getStatus() instanceof UpcomingAppointment) {
                if (appointment.cancel()) {
                    managerFactory.getAppointmentManager().cancelStatus(id);
                    availableTimeIntervalList.getByAvailableTimeInterval(new AvailableTimeInterval(appointment.getDate(), appointment.getTimeInterval())).decreaseAmount();
                }
            }
            else
                throw new IllegalStateException("You cannot cancel a finished or cancelled appointment");
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }
    }

    @Override
    public synchronized void rescheduleAppointment(int id, LocalDate date, TimeInterval timeInterval) throws RemoteException {
        try {
            Appointment appointment = appointmentList.getAppointmentById(id);
            if (appointment.getStatus() instanceof UpcomingAppointment) {
                if (!availableTimeIntervalList.getByAvailableTimeInterval(new AvailableTimeInterval(date,timeInterval)).isAvailable())
                    throw new IllegalStateException("The selected time is no longer available");
                if (appointmentList.hasAppointment(appointment.getPatient(), date, timeInterval))
                    throw new IllegalStateException("You already have an appointment at the selected time");
                availableTimeIntervalList.getByAvailableTimeInterval(new AvailableTimeInterval(appointment.getDate(), appointment.getTimeInterval())).decreaseAmount();
                appointment.reschedule(date, timeInterval, getWorkingNurse(date, timeInterval));
                availableTimeIntervalList.getByAvailableTimeInterval(new AvailableTimeInterval(date,timeInterval)).increaseAmount();
                managerFactory.getAppointmentManager().rescheduleAppointment(id, date, timeInterval);
            }
            else
                throw new IllegalStateException("You cannot reschedule a finished or cancelled appointment");
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }
    }

    @Override
    public synchronized void logout(User user) {
        if (onlineList.contains(user.getCpr())) {
            onlineList.removeByCpr(user.getCpr());
        }
        else {
            throw new IllegalStateException("That user is not logged in");
        }
    }

    @Override
    public synchronized UserList getUsersByCprAndName(String criteria, String typeOfList) {
        switch(typeOfList) {
            case "Patient List":
                return userList.getPatientList().getUsersByCprAndName(criteria);
            case "Administrator List":
                return userList.getAdminList().getUsersByCprAndName(criteria);
            case "Nurse List":
                return userList.getNurseList().getUsersByCprAndName(criteria);
        }
        return null;
    }

    @Override
    public synchronized Patient getPatient(String cpr) {
        return userList.getPatient(cpr);
    }

    @Override
    public synchronized void changeResult(int id, Result result) throws RemoteException {
        try {
            TestAppointment appointment = (TestAppointment) appointmentList.getAppointmentById(id);
            appointment.setResult(result);
            managerFactory.getAppointmentManager().changeResult(appointment);
            updateList();
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }

    }
    
    @Override
    public AppointmentList getUpcomingAppointments(Patient patient) {
        return appointmentList.getUpcomingAppointments(patient);
    }
    
    @Override
    public synchronized VaccineStatus updateVaccineStatus(Patient patient) throws RemoteException {
        try{
            managerFactory.getPatientManager().setVaccineStatus(patient.getCpr(), patient.getVaccineStatus());
            updateList();
            return patient.getVaccineStatus();
        }
        catch (SQLException e){
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }
    }

    @Override public synchronized void setRole(User user, String role) throws RemoteException {
        switch (role) {
            case "Nurse":
                try {
                    Nurse nurse = new Nurse(user.getCpr(), user.getPassword(), user.getFirstName(), user.getMiddleName(), user.getLastName(), user.getAddress(), user.getPhone(),
                    user.getEmail(), generateEmployeeId(user.getFirstName(), user.getMiddleName(), user.getLastName()));
                    managerFactory.getNurseManager().addNurse(nurse);
                }
                catch (SQLException e) {
                    e.printStackTrace();
                    throw new RemoteException(e.getMessage());
                }
                break;
            case "Administrator":
                try {
                    Administrator administrator = new Administrator(user.getCpr(), user.getPassword(), user.getFirstName(), user.getMiddleName(), user.getLastName(), user.getAddress(),
                    user.getPhone(), user.getEmail(), generateEmployeeId(user.getFirstName(), user.getMiddleName(), user.getLastName()));
                    managerFactory.getAdministratorManager().addAdministrator(administrator);
                }
                catch (SQLException e) {
                    e.printStackTrace();
                    throw new RemoteException(e.getMessage());
                }
                break;
        }
        updateList();
    }

    @Override public synchronized void RemoveRole(User user) throws RemoteException {
        switch (user.getClass().getSimpleName()) {
            case "Nurse":
                for (Appointment appointment : getNurseUpcomingAppointments(userList.getNurse(user.getCpr())).getAppointments())
                    cancelAppointment(appointment.getId());
                userList.getNurseList().remove(user);
                loadAvailableTimeIntervals();
                try {
                    managerFactory.getNurseManager().updateAccess((Nurse) user, false);
                    for(Schedule schedule: ((Nurse) user).getScheduleList().getSchedules())
                        editSchedule((Nurse)user,schedule.getDateFrom(), 0);
                }
                catch (SQLException e) {
                    e.printStackTrace();
                    throw new RemoteException(e.getMessage());
                }
                break;
            case "Administrator":
                userList.remove(user);
                try {
                    managerFactory.getAdministratorManager().updateAccess((Administrator) user, false);
                }
                catch (SQLException e) {
                    e.printStackTrace();
                    throw new RemoteException(e.getMessage());
                }
                break;
        }
        updateList();
    }

    @Override
    public synchronized void addFAQ(String question, String answer, Category category, Administrator creator) throws RemoteException {
        try {
            FAQValidator.validateNewFAQ(question, answer, category, creator);
            FAQ faq = managerFactory.getFAQManager().addFAQ(question, answer, category, creator);
            faqList.add(faq);
            property.firePropertyChange("FAQ",null, faq);
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }
    }

    @Override
    public synchronized void editFAQ(FAQ faq, String question, String answer, Category category) throws RemoteException {
        try {
            FAQValidator.validateEditFAQ(question, answer, category);
            FAQ faq1 = getFAQList().getFAQ(faq.getQuestion(), faq.getAnswer());
            managerFactory.getFAQManager().updateFAQ(faq1, question, answer, category);
            faq1.setQuestion(question);
            faq1.setAnswer(answer);
            faq1.setCategory(category);
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }
    }

    @Override
    public synchronized void removeFAQ(String question, String answer) throws RemoteException {
        try{
            FAQ faq = faqList.getFAQ(question, answer);
            if(faq != null) {
                faqList.remove(faq);
                managerFactory.getFAQManager().removeFAQ(question, answer);
                property.firePropertyChange("FAQRemove", null, faq);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }
    }

    @Override
    public synchronized FAQList getFAQList() {
        return faqList;
    }
    
    @Override
    public synchronized void sendMessage(Patient patient, String message, Administrator administrator) throws RemoteException {
        try {
            MessageValidator.validateMessage(message, patient);
            patient = getPatient(patient.getCpr());
            message = message.trim();
            Message newMessage = managerFactory.getChatManager().addMessage(message, LocalDate.now(), LocalTime.now(), new UnreadStatus(), patient, administrator);
            
            if (administrator != null) {
                // Admin replied so mark all unread patient messages as read - keep messages from admin unread until the patient sends a reply
                for (Message m : patient.getChatLog().getUnreadMessages()) {
                    if (m.getAdministrator() == null) {
                        m.read();
                        managerFactory.getChatManager().readMessage(m);
                    }
                }
            }
            else {
                // Patient replied so mark all unread admin messages as read - keep messages from patient unread until an admin sends a reply
                for (Message m : patient.getChatLog().getUnreadMessages()) {
                    if (m.getAdministrator() != null) {
                        m.read();
                        managerFactory.getChatManager().readMessage(m);
                    }
                }
            }
            
            patient.getChatLog().add(newMessage);
            property.firePropertyChange("PatientMessage", patient, newMessage);
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }
    }
    
    @Override
    public List<Message> getUnreadMessages(Patient patient) {
        return patient.getChatLog().getUnreadMessages();
    }
    
    @Override
    public boolean isPatientChatBeingViewed(String cpr) {
        return userList.getPatient(cpr).getChatLog().isChatLocked();
    }
    
    @Override
    public void lockChat(String cpr, boolean locked) {
        ChatLog chatLog = userList.getPatient(cpr).getChatLog();
        chatLog.setLocked(locked);
    }
    
    @Override
    public synchronized void close() {
        property.close();
    }

    @Override public boolean addListener(GeneralListener<Object, Object> listener, String... propertyNames) {
        return property.addListener(listener,propertyNames);
    }

    @Override public boolean removeListener(GeneralListener<Object, Object> listener, String... propertyNames) {
        return property.removeListener(listener,propertyNames);
    }
}
