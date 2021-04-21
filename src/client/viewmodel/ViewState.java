package client.viewmodel;


import server.model.Appointment;
import server.model.User;

public class ViewState {

    private User user;
    private Appointment selectedAppointment;

    public ViewState() {
        user = null;
        selectedAppointment = null;
    }
    public Appointment getSelectedAppointment(){
        return selectedAppointment;
    }
    public void setSelectedAppointment(Appointment appointment){
        this.selectedAppointment = appointment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
