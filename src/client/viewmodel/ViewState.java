package client.viewmodel;

import server.model.domain.User;

public class ViewState {

    private User user;
    private int selectedAppointment;

    public ViewState() {
        user = null;
        selectedAppointment = -1;
    }
    public int getSelectedAppointment(){
        return selectedAppointment;
    }
    public void setSelectedAppointment(int appointment){
        this.selectedAppointment = appointment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
