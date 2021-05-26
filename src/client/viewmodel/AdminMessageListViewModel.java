package client.viewmodel;

import client.model.Model;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import server.model.domain.chat.Message;
import server.model.domain.user.Patient;
import server.model.domain.user.User;
import server.model.domain.user.UserList;
import utility.observer.event.ObserverEvent;
import utility.observer.listener.LocalListener;

public class AdminMessageListViewModel implements AdminMessageListViewModelInterface, LocalListener<Object, Object> {
    private Model model;
    private ViewState<User> viewState;
    private ObservableList<MessageTableDataViewModel> tableData;
    private ObjectProperty<MessageTableDataViewModel> selectedChat;
    private BooleanProperty showReadMessages;
    private StringProperty error;
    private ObjectProperty<Paint> errorFill;
    
    public AdminMessageListViewModel(Model model, ViewState<User> viewState) {
        this.model = model;
        model.addListener(this, "PatientMessage", "NewPatient");
        this.viewState = viewState;
        tableData = FXCollections.observableArrayList();
        selectedChat = new SimpleObjectProperty<>(null);
        showReadMessages = new SimpleBooleanProperty(true);
        error = new SimpleStringProperty("");
        errorFill = new SimpleObjectProperty<>(Color.RED);
        
        loadFromModel();
    }
    
    private void loadFromModel() {
        tableData.clear();
        UserList patientList = model.getPatients();
        for (User user : patientList.getUsers()) {
            Patient patient = (Patient) user;
            tableData.add(new MessageTableDataViewModel(patient));
        }
    }
    
    @Override
    public boolean enterChat() {
        if (selectedChat.get() != null) {
            viewState.setSelectedUser(model.getPatients().getUserByCpr(selectedChat.get().getCprProperty().get()));
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
    
    private void add(Patient patient) {
        tableData.add(new MessageTableDataViewModel(patient));
    }
    
    private void edit(Patient patient) {
        for (int i = 0; i < tableData.size(); i++) {
            MessageTableDataViewModel data = tableData.get(i);
            if (data.getCprProperty().get().equals(patient.getCpr())) {
                tableData.remove(i);
                tableData.add(i, new MessageTableDataViewModel(patient));
                break;
            }
        }
    }
    
    @Override
    public void propertyChange(ObserverEvent<Object, Object> event) {
        Platform.runLater(() -> {
            switch (event.getPropertyName()) {
                case "NewPatient":
                    Patient patient = (Patient) event.getValue1();
                    add(patient);
                    break;
                case "PatientMessage":
                    Message message = (Message) event.getValue2();
                    edit(message.getPatient());
                    break;
            }
        });
    }
}
