package client.viewmodel;

import client.model.MessageModel;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import server.model.domain.chat.Chat;
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
    private MessageModel model;

    public PatientChatViewModel(MessageModel model, ViewState viewState) {
        this.model =  model;
        this.viewState =  viewState;
        this.errorLabel = new SimpleStringProperty();
        this.updated = new SimpleBooleanProperty(false);
        this.user =  new SimpleStringProperty();
        this.textArea = new SimpleStringProperty();
        this.messages = FXCollections.observableArrayList();
        model.addListener(this,"PatientMessage");
    }
    public void loadChatList() {
        if(viewState.getUser() instanceof Patient) {
            Chat chat = ((Patient) viewState.getUser()).getChat();
            for(Message message : chat.getMessages()) {
                addMessageBox(message);
            }
        }
        else {

        }
    }

    public void reset(){
        user.set(((Patient)viewState.getUser()).getFirstName());
        errorLabel.set("");
        textArea.set("");
    }

    private void addMessageBox(Message message) {
        updated.set(false);
        VBox newMessage = new VBox();
        newMessage.setPadding(new Insets(10,15,5,10));
        newMessage.setMaxHeight(Double.MAX_VALUE);
        newMessage.setPrefWidth(465);
        newMessage.setMinWidth(newMessage.getPrefWidth());
        newMessage.setAlignment(Pos.CENTER_RIGHT);

        Label username = new Label(message.getSender().getFirstName());
        Label text = new Label(message.getMessage());
        username.setFont(new Font(15));
        username.setMaxWidth(newMessage.getPrefWidth());
        text.setFont(new Font(15));
        text.setMaxWidth(newMessage.getPrefWidth());
        text.setWrapText(true);
        newMessage.getChildren().add(username);
        newMessage.getChildren().add(text);
        messages.add(newMessage);
        updated.set(true);
    }

    @Override
    public void sendMessage() {
        try{
            model.sendMessage((Patient)viewState.getUser(),textArea.get());
            reset();
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
            if(viewState.getUser().equals(observerEvent.getValue1()) || viewState.getUser() instanceof Administrator)
                addMessageBox((Message) observerEvent.getValue2());
        });
    }
}
