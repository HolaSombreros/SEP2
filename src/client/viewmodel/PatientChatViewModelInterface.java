package client.viewmodel;

import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.scene.Node;

public interface PatientChatViewModelInterface {
    void reset();
    void sendMessage();
    StringProperty getErrorLabelProperty();
    StringProperty getUserProperty();
    StringProperty getTextAreaProperty();
    ObservableList<Node> getMessages();
}
