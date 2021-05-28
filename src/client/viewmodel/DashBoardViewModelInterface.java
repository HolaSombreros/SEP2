package client.viewmodel;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

public interface DashBoardViewModelInterface {
    void reset();

    void logout();

    void applyForVaccination();

    void enterChat();

    StringProperty getUsernameProperty();

    StringProperty getTimeProperty();

    StringProperty getDateProperty();

    StringProperty getVaccinationLabelProperty();

    BooleanProperty getDisableButtonProperty();

    StringProperty getNextAppointmentProperty();
    ObservableList<VBox> getNotifications();
    BooleanProperty getUpdatedProperty();
}
