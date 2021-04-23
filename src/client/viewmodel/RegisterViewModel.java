package client.viewmodel;

import client.model.Model;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class RegisterViewModel implements RegisterViewModelInterface {
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
    
    @Override
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
    
    @Override
    public StringProperty getFirstNameProperty() {
        return firstName;
    }
    
    @Override
    public StringProperty getMiddleNameProperty() {
        return middleName;
    }
    
    @Override
    public StringProperty getLastNameProperty() {
        return lastName;
    }
    
    @Override
    public StringProperty getCPRProperty() {
        return cpr;
    }
    
    @Override
    public StringProperty getPasswordProperty() {
        return password;
    }
    
    @Override
    public StringProperty getStreetProperty() {
        return street;
    }
    
    @Override
    public StringProperty getNumberProperty() {
        return number;
    }
    
    @Override
    public IntegerProperty getZipCodeProperty() {
        return zipCode;
    }
    
    @Override
    public StringProperty getCityProperty() {
        return city;
    }
    
    @Override
    public StringProperty getPhoneProperty() {
        return phone;
    }
    
    @Override
    public StringProperty getEmailProperty() {
        return email;
    }
    
    @Override
    public StringProperty getErrorProperty() {
        return error;
    }
    
    @Override
    public boolean register() {
        try {
            model.register(cpr.get(), password.get(), firstName.get(), middleName.get(), lastName.get(), phone.get(), email.get(), street.get(), number.get(), zipCode.get(), city.get());
            error.set("");
            return true;
        }
        catch (Exception e) {
            error.set(e.getMessage());
            return false;
        }
    }
}
