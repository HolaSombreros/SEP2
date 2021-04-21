package client.viewmodel;

import client.model.Model;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

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
        System.out.println(viewState.getUser());
        if(viewState.getUser().getType().equals("Nurse"))
            roleProperty.set(viewState.getUser().getType());
        if(viewState.getUser().getType().equals("Administrator"))
            roleProperty.set(viewState.getUser().getType());
    }

    public StringProperty roleProperty() {
        return roleProperty;
    }
}
