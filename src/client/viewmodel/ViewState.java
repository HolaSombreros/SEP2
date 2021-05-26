package client.viewmodel;

import server.model.domain.faq.FAQ;
import server.model.domain.user.User;

public class ViewState {
    private User user;
    private User selectedUser;
    private int selectedAppointment;
    private FAQ selectedFAQ;

    public ViewState() {
        user = null;
        selectedAppointment = -1;
        selectedUser = null;
        selectedFAQ = null;
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
    
    public FAQ getSelectedFAQ() {
        return selectedFAQ;
    }

    public void setSelectedFAQ(FAQ faq) {
        this.selectedFAQ = faq;
    }

    public void removeSelectedFAQ() {
        selectedFAQ = null;
    }
}
