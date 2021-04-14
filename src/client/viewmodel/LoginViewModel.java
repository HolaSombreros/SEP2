package client.viewmodel;

import client.model.Model;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import server.model.Administrator;
import server.model.Nurse;
import server.model.Patient;

public class LoginViewModel {

    private StringProperty usernameProperty;
    private StringProperty passwordProperty;
    private StringProperty errorProperty;
    private Model model;

    public LoginViewModel(Model model) {
        this.model = model;
        usernameProperty = new SimpleStringProperty("");
        passwordProperty = new SimpleStringProperty("");
        errorProperty = new SimpleStringProperty("");
    }

    public int login(){
        if(usernameProperty.get().equals("") || passwordProperty.get().equals("")) {
            errorProperty.setValue("Please enter a valid Cpr or Password");
            return 0;
        }
        Patient loggedIn = model.login(usernameProperty.get(), passwordProperty.get());
        if (loggedIn == null) {
            errorProperty.set("CPR and password do not match");
            return 0;
        }
        else {
            // TODO: Change the return type of this, perhaps to 0 for false, 1 for patient, 2 for admin/nurse so we can differentiate between 3 things in the viewcontroller
            if (loggedIn instanceof Administrator ||
                loggedIn instanceof Nurse) {
                // Account is a Admin / Nurse
                return 2;
            }
            else {
                // Account is only a Patient
                return 1;
            }
        }
    }

    public void reset(){
        errorProperty.setValue("");
        usernameProperty.setValue("");
        passwordProperty.setValue("");
    }

    public StringProperty getUsernameProperty() {
        return usernameProperty;
    }

    public StringProperty getPasswordProperty() {
        return passwordProperty;
    }

    public StringProperty getErrorProperty() {
        return errorProperty;
    }
}
