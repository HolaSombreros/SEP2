package client.viewmodel;

import client.model.MessageModel;
import client.model.Model;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import server.model.domain.user.User;

public class AdminMessageListViewModel implements AdminMessageListViewModelInterface {
    private Model model;
    private ViewState<User> viewState;
    private ObservableList<MessageTableDataViewModel> tableData;
    private ObjectProperty<MessageTableDataViewModel> selectedChat;
    private BooleanProperty showReadMessages;
    private StringProperty error;
    private ObjectProperty<Paint> errorFill;
    
    public AdminMessageListViewModel(Model model, ViewState<User> viewState) {
        this.model = model;
        this.viewState = viewState;
        tableData = FXCollections.observableArrayList();
        selectedChat = new SimpleObjectProperty<>(null);
        showReadMessages = new SimpleBooleanProperty(true);
        error = new SimpleStringProperty("");
        errorFill = new SimpleObjectProperty<>(Color.RED);
    }
    
    @Override
    public boolean enterChat() {
        if (selectedChat.get() != null) {
            viewState.setSelectedChat(model.getPatients().getUserByCpr(selectedChat.get().getCprProperty().get()).getChat());
            return true;
        }
        else {
            error.set("Please select the chat you want to open");
            errorFill.set(Color.RED);
            return false;
        }
    }
    
    @Override
    public ObservableList<MessageTableDataViewModel> getTableData() {
        return tableData;
    }
    
    @Override
    public BooleanProperty showReadMessagesProperty() {
        return showReadMessages;
    }
    
    @Override
    public void setSelectedChat(MessageTableDataViewModel chat) {
        selectedChat.set(chat);
    }
    
    @Override
    public StringProperty getErrorProperty() {
        return error;
    }
    
    @Override
    public ObjectProperty<Paint> getErrorFillProperty() {
        return errorFill;
    }
    
    @Override
    public void reset() {
        selectedChat.set(null);
        error.set("");
        errorFill.set(Color.RED);
    }
}
