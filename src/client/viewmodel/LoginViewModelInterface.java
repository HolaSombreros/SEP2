package client.viewmodel;

import javafx.beans.property.StringProperty;

public interface LoginViewModelInterface {
    int login();
    void reset();
    
    StringProperty getCprProperty();
    StringProperty getPasswordProperty();
    StringProperty getErrorProperty();
}
