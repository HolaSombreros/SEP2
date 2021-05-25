package client.viewmodel;

import client.model.MessageModel;
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
import server.model.domain.user.Patient;
import server.model.domain.user.User;
import utility.observer.listener.LocalListener;

public class PatientChatViewModel implements PatientChatViewModelInterface
{

    private StringProperty errorLabel;
    private StringProperty user;
    private StringProperty textArea;
    private ObservableList<Node> messages;
    private ViewState viewState;
    private MessageModel model;

    public PatientChatViewModel(MessageModel model, ViewState viewState) {
        this.model =  model;
        this.viewState =  viewState;
        this.errorLabel = new SimpleStringProperty();
        this.user =  new SimpleStringProperty();
        this.textArea = new SimpleStringProperty();
        this.messages = FXCollections.observableArrayList();
    }
    public void reset(){
        user.set(((Patient)viewState.getUser()).getFirstName());
        errorLabel.set("");
        textArea.set("");
    }


    @Override
    public void addMessageBox(Message message) {
        HBox newMessage = new HBox();
        newMessage.setAlignment(Pos.CENTER_RIGHT);
        newMessage.setPrefHeight(Region.USE_COMPUTED_SIZE);
        newMessage.setPrefWidth(Region.USE_COMPUTED_SIZE);
        newMessage.setBackground(new Background(new BackgroundFill(Color.AQUAMARINE, CornerRadii.EMPTY,
                Insets.EMPTY)));
        Label sendMessage = new Label(((Patient)viewState.getUser()).getFirstName() + " " + message.getMessage());
        sendMessage.setMaxWidth(newMessage.getPrefWidth());
        sendMessage.setWrapText(true);
        newMessage.getChildren().add(sendMessage);
        messages.add(newMessage);
    }

    @Override
    public void sendMessage() {
        if(textArea.get() != null && !textArea.get().trim().isEmpty()) {
            //model adds the message
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
    public ObservableList<Node> getMessages() {
        return messages;
    }

}
