package client.viewmodel;

import client.model.Model;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import server.model.Address;

public class RegisterViewModel {
    private Model model;
    private StringProperty firstName;
    private StringProperty middleName;
    private StringProperty lastName;
    private StringProperty cpr;
    private StringProperty password;
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
        street = new SimpleStringProperty();
        number = new SimpleStringProperty();
        zipCode = new SimpleIntegerProperty();
        city = new SimpleStringProperty();
        phone = new SimpleStringProperty();
        email = new SimpleStringProperty();
        error = new SimpleStringProperty();
    }
    
    public void reset() {
        firstName.set("");
        middleName.set("");
        lastName.set("");
        cpr.set("");
        password.set("");
        street.set("");
        number.set("");
        zipCode.setValue(null);
        city.set("");
        phone.set("");
        email.set("");
        error.set("");
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
    
    public boolean register() {
        try {
            Address address = new Address(street.get(), number.get(), zipCode.get(), city.get());
            model.register(cpr.get(), password.get(), firstName.get(), middleName.get(), lastName.get(), address, phone.get(), email.get());
            error.set("");
            return true;
        }
        catch (Exception e) {
            error.set(e.getMessage());
            return false;
        }
    }
}
