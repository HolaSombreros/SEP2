package client.viewmodel;

import client.model.Model;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import server.model.domain.Patient;

public class DashBoardViewModel implements DashBoardViewModelInterface {
    private Model model;
    private ViewState viewState;
    
    private StringProperty username;
    private StringProperty access;
    private BooleanProperty accessVisibility;
    private StringProperty time;
    private StringProperty date;
    
    public DashBoardViewModel(Model model, ViewState viewState) {
        this.model = model;
        this.viewState = viewState;
        
        username = new SimpleStringProperty();
        access = new SimpleStringProperty();
        accessVisibility = new SimpleBooleanProperty();
        time = new SimpleStringProperty();
        date = new SimpleStringProperty();
    }
    
    @Override
    public void reset() {
        username.set(viewState.getUser().getFirstName());
        if (!(viewState.getUser() instanceof Patient)) {
            access.set(viewState.getUser().getClass().getSimpleName());
            accessVisibility.set(true);
        }
        else {
            accessVisibility.set(false);
        }
        time.set("no timer, yet");
        date.set("no date, yet");
    }
    
    @Override
    public void logout() {
        model.logout(viewState.getUser());
        viewState.removeUser();
    }
    
    @Override
    public StringProperty getUsernameProperty() {
        return username;
    }
    
    @Override
    public StringProperty getAccessProperty() {
        return access;
    }
    
    @Override
    public BooleanProperty getAccessVisibilityProperty() {
        return accessVisibility;
    }
    
    @Override
    public StringProperty getTimeProperty() {
        return time;
    }
    
    @Override
    public StringProperty getDateProperty() {
        return date;
    }
}
