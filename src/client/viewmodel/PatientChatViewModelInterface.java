package client.viewmodel;

import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import server.model.domain.chat.Message;

public interface PatientChatViewModelInterface {
    void reset();
    void addMessageBox(Message message);
    void sendMessage();
    StringProperty getErrorLabelProperty();
    StringProperty getUserProperty();
    StringProperty getTextAreaProperty();
    ObservableList<Node> getMessages();
}
