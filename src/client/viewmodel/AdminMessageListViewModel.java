package client.viewmodel;

import client.model.Model;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import server.model.domain.chat.Message;
import server.model.domain.user.Administrator;
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
        model.addListener(this, "PatientMessage");
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
            viewState.setSelectedChat(((Patient)(model.getPatients().getUserByCpr(selectedChat.get().getCprProperty().get()))).getChat());
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
    
    private void edit(Patient patient) {
        for (int i = 0; i < tableData.size(); i++) {
            MessageTableDataViewModel data = tableData.get(i);
            if (data.getCprProperty().get().equals(patient.getCpr())) {
                System.out.println(patient.getChat().size());
                tableData.remove(i);
                tableData.add(i, new MessageTableDataViewModel(patient));
                break;
            }
        }
    }
    
    @Override
    public void propertyChange(ObserverEvent<Object, Object> event) {
        Message message = (Message) event.getValue2();
        // Technically unnecessary I think but oh well
        if (viewState.getUser() instanceof Administrator) {
            Patient patient = message.getPatient();
            edit(patient);
        }
    }
}
