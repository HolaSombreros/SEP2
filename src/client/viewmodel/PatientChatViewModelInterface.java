package client.viewmodel;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.scene.Node;

public interface PatientChatViewModelInterface {
    void reset();
    void sendMessage();
    StringProperty getErrorLabelProperty();
    StringProperty getUserProperty();
    StringProperty getTextAreaProperty();
    BooleanProperty getUpdatedProperty();
    ObservableList<Node> getMessages();
}
