package client.viewmodel;

import javafx.beans.property.BooleanProperty;
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
    BooleanProperty approveButtonProperty();
    BooleanProperty declineButtonProperty();
    StringProperty titleProperty();
    boolean isAdmin();
    void approve();
    void decline();

    void reset();
    void editDetails();

}
