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
import javafx.scene.paint.Color;
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
        model.addListener(this,"patientMessage");
    }

    public void reset(){
        user.set(((Patient)viewState.getUser()).getFirstName());
        errorLabel.set("");
        textArea.set("");
    }

    private void addMessageBox(Message message) {
        updated.set(false);
        HBox newMessage = new HBox();
        newMessage.setAlignment(Pos.CENTER_RIGHT);
        newMessage.setPrefHeight(Region.USE_COMPUTED_SIZE);
        newMessage.setPrefWidth(Region.USE_COMPUTED_SIZE);
        newMessage.setBackground(new Background(new BackgroundFill(Color.AQUAMARINE, CornerRadii.EMPTY,
                Insets.EMPTY)));
        Label sendMessage = new Label(((Patient)viewState.getUser()).getFirstName() + " " + message);
        sendMessage.setMaxWidth(newMessage.getPrefWidth());
        sendMessage.setWrapText(true);
        newMessage.getChildren().add(sendMessage);
        messages.add(newMessage);
        updated.set(true);
    }

    @Override
    public void sendMessage() {
        if(textArea.get() != null && !textArea.get().trim().isEmpty()) {
            model.sendMessage((Patient)viewState.getUser(),textArea.get());
            System.out.println("haha");
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
        Platform.runLater(() ->{
            if(viewState.getUser().equals(observerEvent.getValue1()) || viewState.getUser() instanceof Administrator) {
                System.out.println("Here");
                addMessageBox((Message) observerEvent.getValue2());
            }
        });

    }
}
