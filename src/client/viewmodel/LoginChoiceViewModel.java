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
        roleProperty.set(viewState.getUser().getType());
    }

    public StringProperty roleProperty() {
        return roleProperty;
    }
}
