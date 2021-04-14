package client.viewmodel;

import client.model.Model;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import server.model.Administrator;
import server.model.Nurse;

public class LoginChoiceViewModel {

    private StringProperty roleProperty;
    private Model model;
    private ViewState viewState;

    public LoginChoiceViewModel(Model model,ViewState viewState) {
        this.model = model;
        roleProperty = new SimpleStringProperty();
        this.viewState = viewState;
    }

    public void reset(){
        roleProperty.set("");
    }


    public void updateRoleProperty(){
        System.out.println(viewState.getPatient());
        if(viewState.getPatient() instanceof Nurse)
            roleProperty.set("Nurse");
        if(viewState.getPatient() instanceof Administrator)
            roleProperty.set("Administrator");
    }

    public StringProperty roleProperty() {
        return roleProperty;
    }
}
