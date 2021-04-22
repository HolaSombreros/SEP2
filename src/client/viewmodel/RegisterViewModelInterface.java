package client.viewmodel;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

public interface RegisterViewModelInterface {
    void reset();
    boolean register();
    
    StringProperty getFirstNameProperty();
    StringProperty getMiddleNameProperty();
    StringProperty getLastNameProperty();
    StringProperty getCPRProperty();
    StringProperty getPasswordProperty();
    StringProperty getStreetProperty();
    StringProperty getNumberProperty();
    IntegerProperty getZipCodeProperty();
    StringProperty getCityProperty();
    StringProperty getPhoneProperty();
    StringProperty getEmailProperty();
    StringProperty getErrorProperty();
}
