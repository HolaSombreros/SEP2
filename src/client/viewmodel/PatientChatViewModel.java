package client.viewmodel;

import client.model.Model;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import server.model.domain.chat.Message;
import server.model.domain.user.Administrator;
import server.model.domain.user.Patient;
import utility.observer.event.ObserverEvent;
import utility.observer.listener.LocalListener;

public class PatientChatViewModel implements PatientChatViewModelInterface, LocalListener<Object, Object>
{
    private StringProperty errorLabel;
    private StringProperty user;
    private StringProperty textArea;
    private ObservableList<Node> messages;
    private BooleanProperty updated;
    private ViewState viewState;
    private Model model;

    public PatientChatViewModel(Model model, ViewState viewState) {
        this.model =  model;
        this.viewState =  viewState;
        this.errorLabel = new SimpleStringProperty();
        this.updated = new SimpleBooleanProperty(false);
        this.user =  new SimpleStringProperty();
        this.textArea = new SimpleStringProperty();
        this.messages = FXCollections.observableArrayList();
        model.addListener(this,"PatientMessage");
    }
    
    @Override
    public void loadChatList() {
        messages.clear();
        for (Message message : model.getPatient(viewState.getSelectedUser().getCpr()).getChatLog().getMessages()) {
            addMessageBox(message);
        }
    }
    
    @Override
    public void exitChat() {
        model.lockChat(viewState.getSelectedUser().getCpr(), false);
        viewState.removeSelectedUser();
    }
    
    @Override
    public boolean isAdmin() {
        return viewState.getUser() instanceof Administrator;
    }
    
    @Override
    public void reset(){
        user.set((viewState.getUser()).getFirstName());
        resetInputs();
        loadChatList();
        
        // Is the actor an admin? Then lock the chat through the model
        if (viewState.getUser() instanceof Administrator) {
        
        }
    }

    private void addMessageBox(Message message) {
        updated.set(false);
        VBox newMessage = new VBox();
        newMessage.setPrefWidth(465);
        newMessage.setMinWidth(newMessage.getPrefWidth());
        String sender;
        if (message.getAdministrator() == null) {
            sender = message.getPatient().getFirstName();
        }
        else {
            sender = message.getAdministrator().getFirstName();
        }
        Label username = new Label(sender);
        Label text = new Label(message.getMessage());
        username.setFont(new Font(12));
        username.setMaxWidth(newMessage.getPrefWidth());
        username.setStyle("-fx-font-weight: bold;");
        text.setFont(new Font(14));
        text.setMaxWidth(newMessage.getPrefWidth());
        text.setStyle("-fx-background-radius: 5px; -fx-padding: 3px 5px 3px 5px; -fx-text-fill: #000000;");
        text.setWrapText(true);
        if (message.getAdministrator() != null) {
            username.setAlignment(Pos.CENTER_LEFT);
            text.setAlignment(Pos.CENTER_LEFT);
        }
        else {
            username.setAlignment(Pos.CENTER_RIGHT);
            text.setAlignment(Pos.CENTER_RIGHT);
        }
        if ((viewState.getUser() instanceof Patient && message.getPatient().equals(viewState.getUser()) && message.getAdministrator() == null) || viewState.getUser().equals(message.getAdministrator())) {
            text.setStyle("-fx-background-color: #BC8F8F;" + text.getStyle());
        }
        else {
            text.setStyle("-fx-background-color: #E9967A;" + text.getStyle());
        }
        newMessage.getChildren().add(username);
        newMessage.getChildren().add(text);
        messages.add(newMessage);
        updated.set(true);
    }
    
    private void resetInputs() {
        errorLabel.set("");
        textArea.set("");
    }

    @Override
    public void sendMessage() {
        try{
            Administrator administrator = null;
            if (viewState.getUser() instanceof Administrator) {
                administrator = ((Administrator) viewState.getUser());
            }
            model.sendMessage((Patient) viewState.getSelectedUser(), textArea.get(), administrator);
            resetInputs();
        }
        catch (Exception e) {
            errorLabel.set(e.getMessage());
        }
    }

    @Override
    public StringProperty getErrorLabelProperty()
    {
        return errorLabel;
    }

    @Override
    public StringProperty getUserProperty()
    {
        return user;
    }

    @Override
    public StringProperty getTextAreaProperty()
    {
        return textArea;
    }

    @Override
    public BooleanProperty getUpdatedProperty()
    {
        return updated;
    }

    @Override
    public ObservableList<Node> getMessages() {
        return messages;
    }

    @Override
    public void propertyChange(ObserverEvent<Object, Object> observerEvent)
    {
        Platform.runLater(() -> {
            if(viewState.getSelectedUser().equals(observerEvent.getValue1()) || viewState.getUser() instanceof Administrator)
                addMessageBox((Message) observerEvent.getValue2());
        });
    }
}
