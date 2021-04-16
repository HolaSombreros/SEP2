package client.viewmodel;


import server.model.User;

public class ViewState {

    private User user;

    public ViewState() {
        user = null;
    }

    public User getUser() {
        return user;
    }

    public void setPatient(User user) {
        this.user = user;
    }
}
