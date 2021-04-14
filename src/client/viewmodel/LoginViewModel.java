package client.viewmodel;

import client.model.Model;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import server.model.Administrator;
import server.model.Nurse;

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

    public boolean login(){
        if(usernameProperty.get().equals("") || passwordProperty.get().equals("")) {
            errorProperty.setValue("Please enter a valid Cpr or Password");
            return false;
        }
        if(model.getPatientByCpr(usernameProperty.toString().trim()) instanceof Nurse || model.getPatientByCpr(usernameProperty.toString().trim()) instanceof Administrator)
            return true;
        else{
            errorProperty.setValue("Successfully logged in as a Patient");
            return false;
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
