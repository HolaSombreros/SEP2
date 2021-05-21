package client.viewmodel;

import server.model.domain.user.Administrator;
import server.model.domain.user.Nurse;
import server.model.domain.user.Patient;
import server.model.domain.user.User;

public class ViewState<T> {
    
    private T user;
    private User selectedUser;
    private int selectedAppointment;
    
    public ViewState() {
        user = null;
        selectedAppointment = -1;
        selectedUser = null;
    }
    
    public int getSelectedAppointment() {
        return selectedAppointment;
    }
    
    public void setSelectedAppointment(int appointment) {
        this.selectedAppointment = appointment;
    }
    
    public void removeSelectedAppointment() {
        selectedAppointment = -1;
    }
    
    public T getUser() {
        return user;
    }
    
    public void setUser(T user) {
        this.user = user;
    }
    
    public void removeUser() {
        user = null;
    }

    public User getSelectedUser()
    {
        return selectedUser;
    }

    public void setSelectedUser(User user)
    {
        this.selectedUser = user;
    }

    public void removeSelectedUser()
    {
        selectedUser = null;
    }

}
