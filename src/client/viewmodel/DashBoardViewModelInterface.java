package client.viewmodel;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;

public interface DashBoardViewModelInterface {
    void reset();
    void logout();
    void applyForVaccination();
    
    StringProperty getUsernameProperty();
    StringProperty getAccessProperty();
    BooleanProperty getAccessVisibilityProperty();
    StringProperty getTimeProperty();
    StringProperty getDateProperty();
    StringProperty getVaccinationLabelProperty();
    BooleanProperty getDisableButtonProperty();
}
