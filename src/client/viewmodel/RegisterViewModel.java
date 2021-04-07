package client.viewmodel;

import client.model.Model;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import server.model.Address;

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
    private IntegerProperty zipCode;
    private StringProperty city;
    private StringProperty phone;
    private StringProperty email;
    private StringProperty error;
    
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
        zipCode = new SimpleIntegerProperty();
        city = new SimpleStringProperty();
        phone = new SimpleStringProperty();
        email = new SimpleStringProperty();
        error = new SimpleStringProperty();
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
    
    public IntegerProperty getZipCodeProperty() {
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
    
    public StringProperty getErrorProperty() {
        return error;
    }
    
    public void register() {
        Address address = new Address(street.get(), number.get(), zipCode.get(), city.get());
        model.register(cpr.get(), password.get(), firstName.get(), middleName.get(), lastName.get(), address, phone.get(), email.get());
    }
    
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
    
    }
}
