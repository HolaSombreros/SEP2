package client.viewmodel;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.scene.paint.Paint;

public interface AdminMessageListViewModelInterface {
    ObservableList<MessageTableDataViewModel> getTableData();
    BooleanProperty showReadMessagesProperty();
    void setSelectedChat(MessageTableDataViewModel chat);
    StringProperty getErrorProperty();
    ObjectProperty<Paint> getErrorFillProperty();
    
    void reset();
    void filterChatLogs();
    boolean enterChat();
}
