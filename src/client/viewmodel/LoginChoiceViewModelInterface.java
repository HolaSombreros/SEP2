package client.viewmodel;

import javafx.beans.property.StringProperty;

public interface LoginChoiceViewModelInterface {
    void reset();
    void logPatient();
    void logSpecialRole();
    
    StringProperty roleProperty();
}
