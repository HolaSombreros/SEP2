package client.viewmodel.user;

import javafx.beans.property.StringProperty;

public interface LoginChoiceViewModelInterface {
    void reset();

    void logPatient();

    StringProperty roleProperty();
}
