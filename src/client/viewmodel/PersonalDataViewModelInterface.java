package client.viewmodel;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

public interface PersonalDataViewModelInterface
{
    StringProperty getFirstName();
    StringProperty getMiddleName();
    StringProperty getLastName();
    StringProperty getEmail();
    StringProperty getNumber();
    StringProperty getStreet();
    StringProperty getCpr();
    StringProperty getPhoneNumber();
    StringProperty getErrorLabel();
    StringProperty getPassword();
    IntegerProperty getZipCode();
    StringProperty getVaccineStatus();

    void reset();
    void editDetails();

}
