package client.viewmodel.user;

import client.model.Model;
import client.viewmodel.ViewState;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import server.model.domain.user.User;

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
        roleProperty.set(viewState.getUser().getClass().getSimpleName());
    }

    @Override
    public void logPatient() {
        viewState.setUser(model.getPatient(((User) viewState.getUser()).getCpr()));
    }


    @Override
    public StringProperty roleProperty() {
        return roleProperty;
    }
}