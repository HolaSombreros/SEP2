package client.viewmodel;

import javafx.beans.property.StringProperty;

public interface PersonalDataViewModelInterface
{
    StringProperty getFirstName();
    StringProperty getMiddleName();
    StringProperty getLastName();
    StringProperty getEmail();
    StringProperty getNumber();
    StringProperty getStreet();
    StringProperty getCity();
    StringProperty getCpr();
    StringProperty getPhoneNumber();
    StringProperty getErrorLabel();

    void reset();
    void editDetails();
    boolean confirmEditing();
}
