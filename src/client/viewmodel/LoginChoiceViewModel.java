package client.viewmodel;

import client.model.Model;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class LoginChoiceViewModel {

    private StringProperty roleProperty;
    private Model model;

    public LoginChoiceViewModel(Model model) {
        this.model = model;
        roleProperty = new SimpleStringProperty();
    }

    public void reset(){
        roleProperty.set("");
    }

    //TODO: implement viewState and link rolePropery to it
    public StringProperty roleProperty() {
        return roleProperty;
    }
}
