package client.viewmodel;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

public interface PersonalDataViewModelInterface
{
    StringProperty getFirstNameProperty();
    StringProperty getMiddleNameProperty();
    StringProperty getLastNameProperty();
    StringProperty getEmailProperty();
    StringProperty getNumberProperty();
    StringProperty getStreetProperty();
    StringProperty getCprProperty();
    StringProperty getPhoneNumberProperty();
    StringProperty getErrorLabelProperty();
    StringProperty getPasswordProperty();
    IntegerProperty getZipCodeProperty();
    StringProperty getVaccineStatusProperty();

    void reset();
    void editDetails();
    boolean isLoggedInAsNurse();
}
