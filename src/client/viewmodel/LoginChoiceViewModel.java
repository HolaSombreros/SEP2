package client.viewmodel;

import client.model.Model;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class LoginChoiceViewModel implements LoginChoiceViewModelInterface {
    
    private StringProperty roleProperty;
    private Model model;
    private ViewState viewState;
    
    public LoginChoiceViewModel(Model model, ViewState viewState) {
        this.model = model;
        roleProperty = new SimpleStringProperty();
        this.viewState = viewState;
    }
    
    @Override
    public void reset() {
        roleProperty.set("");
        roleProperty.set(viewState.getUser().getClass().getSimpleName());
    }
    
    @Override
    public StringProperty roleProperty() {
        return roleProperty;
    }
}
