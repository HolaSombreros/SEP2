package client.viewmodel;

import client.model.Model;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import server.model.domain.user.Administrator;
import server.model.domain.user.Nurse;

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
        viewState.removeAdmin();
        viewState.removeNurse();
    }

    @Override
    public void logSpecialRole()
    {
        if(roleProperty.get().equals("Administrator"))
            viewState.setAdmin((Administrator) viewState.getUser());
        else viewState.setNurse((Nurse) viewState.getUser());
    }

    @Override
    public StringProperty roleProperty() {
        return roleProperty;
    }
}
