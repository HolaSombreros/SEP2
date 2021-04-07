package client.viewmodel;

import client.model.Model;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class RegisterViewModel implements PropertyChangeListener {
    private Model model;
    private StringProperty firstName;
    private StringProperty middleName;
    private StringProperty lastName;
    private StringProperty cpr;
    private StringProperty password;
    private StringProperty confirmPassword;
    private StringProperty street;
    private StringProperty number;
    private StringProperty zipCode;
    private StringProperty city;
    private StringProperty phone;
    private StringProperty email;
    
    public RegisterViewModel(Model model) {
        this.model = model;
        firstName = new SimpleStringProperty();
        middleName = new SimpleStringProperty();
        lastName = new SimpleStringProperty();
        cpr = new SimpleStringProperty();
        password = new SimpleStringProperty();
        confirmPassword = new SimpleStringProperty();
        street = new SimpleStringProperty();
        number = new SimpleStringProperty();
        zipCode = new SimpleStringProperty();
        city = new SimpleStringProperty();
        phone = new SimpleStringProperty();
        email = new SimpleStringProperty();
        model.addListener(this);
    }
    
    public void reset() {
    
    }
    
    public StringProperty getFirstNameProperty() {
        return firstName;
    }
    
    public StringProperty getMiddleNameProperty() {
        return middleName;
    }
    
    public StringProperty getLastNameProperty() {
        return lastName;
    }
    
    public StringProperty getCPRProperty() {
        return cpr;
    }
    
    public StringProperty getPasswordProperty() {
        return password;
    }
    
    public StringProperty getConfirmPasswordProperty() {
        return confirmPassword;
    }
    
    public StringProperty getStreetProperty() {
        return street;
    }
    
    public StringProperty getNumberProperty() {
        return number;
    }
    
    public StringProperty getZipCodeProperty() {
        return zipCode;
    }
    
    public StringProperty getCityProperty() {
        return city;
    }
    
    public StringProperty getPhoneProperty() {
        return phone;
    }
    
    public StringProperty getEmailProperty() {
        return email;
    }
    
    public void register() {
    
    }
    
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
    
    }
}
