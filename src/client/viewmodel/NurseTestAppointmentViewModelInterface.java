package client.viewmodel;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

public interface NurseTestAppointmentViewModelInterface {
    void reset();
    StringProperty getPatientNameProperty();
    StringProperty patientCprProperty();
    StringProperty statusProperty();
    StringProperty errorPropertyProperty();
    ObservableList<String> getResultList();
    ObjectProperty<String> resultProperty();
    StringProperty timeIntervalProperty();
    StringProperty dateProperty();
    void loadResultTypes();
    void changeResult();
    void saveChanges();


}
