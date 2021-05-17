package client.viewmodel;

import server.model.domain.user.Administrator;
import server.model.domain.user.Patient;
import server.model.domain.user.User;

public class ViewState {
    
    private User user;
    private User selectedUser;
    private int selectedAppointment;
    private Patient patient;
    private Administrator admin;
    
    public ViewState() {
        user = null;
        selectedAppointment = -1;
        selectedUser = null;
        patient = null;
        admin = null;
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
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
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

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
    
    public void removePatient() {
        patient = null;
    }

    public Administrator getAdmin() {
        return admin;
    }

    public void setAdmin(Administrator admin) {
        this.admin = admin;
    }

    public void removeAdmin(){
        admin = null;
    }
}
