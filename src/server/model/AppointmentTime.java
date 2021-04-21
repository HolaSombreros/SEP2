package server.model;

public class AppointmentTime {
    private Date date;
    private TimeInterval timeInterval;
    private AppointmentList appointmentList;
    private UserList nurseList;
    
    public AppointmentTime(Time to, Time from) {
        appointmentList = new AppointmentList();
        timeInterval = new TimeInterval(to, from);
        nurseList = new UserList();
        date = new Date();
    }
    
    public Date getDate() {
        return date.copy();
    }
    
    public TimeInterval getTimeInterval() {
        return timeInterval;
    }
    
    public AppointmentList getAppointmentList() {
        return appointmentList;
    }
    
    public UserList getNurseList() {
        return nurseList;
    }
}
