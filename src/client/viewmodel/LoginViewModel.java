package client.viewmodel;

import client.model.Model;
import client.model.UserModel;
import client.view.View;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import server.model.domain.user.Staff;
import server.model.domain.user.User;

public class LoginViewModel implements LoginViewModelInterface {
    private StringProperty cprProperty;
    private StringProperty passwordProperty;
    private StringProperty errorProperty;
    private UserModel userModel;
    private ViewState viewState;

    public LoginViewModel(UserModel userModel, ViewState viewState) {
        this.userModel = userModel;
        this.viewState = viewState;
        cprProperty = new SimpleStringProperty("");
        passwordProperty = new SimpleStringProperty("");
        errorProperty = new SimpleStringProperty("");
    }
    
    @Override
    public void reset(){
        errorProperty.setValue("");
        cprProperty.setValue("");
        passwordProperty.setValue("");
    }
    
    @Override
    public int login(){
        try {
            User loggedIn = userModel.login(cprProperty.get(), passwordProperty.get());
            viewState.setUser(loggedIn);
            if (loggedIn instanceof Staff) {
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
    
    @Override
    public StringProperty getCprProperty() {
        return cprProperty;
    }
    
    @Override
    public StringProperty getPasswordProperty() {
        return passwordProperty;
    }
    
    @Override
    public StringProperty getErrorProperty() {
        return errorProperty;
    }
}
