package client.viewmodel;

import client.model.Model;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import server.model.Patient;
import server.model.User;

public class LoginViewModel {

    private StringProperty cprProperty;
    private StringProperty passwordProperty;
    private StringProperty errorProperty;
    private Model model;
    private ViewState viewState;

    public LoginViewModel(Model model,ViewState viewState) {
        this.model = model;
        this.viewState = viewState;
        cprProperty = new SimpleStringProperty("");
        passwordProperty = new SimpleStringProperty("");
        errorProperty = new SimpleStringProperty("");
    }

    public int login(){
        try {
            User loggedIn = model.login(cprProperty.get(), passwordProperty.get());
            viewState.setUser(loggedIn);
            if (loggedIn.getType().equals("Administrator") ||
                loggedIn.getType().equals("Nurse")) {
                // Account is a Admin / Nurse
                return 2;
            }
            else {
                // Account is only a Patient
                return 1;
            }
        }
        catch (Exception e) {
            errorProperty.set(e.getMessage());
            return 0;
        }
    }

    public void reset(){
        errorProperty.setValue("");
        cprProperty.setValue("");
        passwordProperty.setValue("");
    }

    public StringProperty getCprProperty() {
        return cprProperty;
    }

    public StringProperty getPasswordProperty() {
        return passwordProperty;
    }

    public StringProperty getErrorProperty() {
        return errorProperty;
    }
}
