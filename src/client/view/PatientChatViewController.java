package client.view;

import client.viewmodel.PatientChatViewModelInterface;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;


public class PatientChatViewController extends ViewController {

    @FXML
    private TextField textField;
    @FXML
    private Label errorLabel;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox chatRoom;
    @FXML
    private Label user;

    private PatientChatViewModelInterface viewModel;

    @Override
    protected void init() {
        viewModel = getViewModelFactory().getPatientChatViewModel();
        textField.textProperty().bindBidirectional(viewModel.getTextAreaProperty());
        errorLabel.textProperty().bind(viewModel.getErrorLabelProperty());
        user.textProperty().bind(viewModel.getUserProperty());
        scrollPane.vvalueProperty().bind(chatRoom.heightProperty());
        Bindings.bindContentBidirectional(viewModel.getMessages(), chatRoom.getChildren());
        viewModel.getUpdatedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) updateChat(viewModel.getMessages());
        });
        reset();
        viewModel.loadChatList();
    }

    private void updateChat(ObservableList<Node> content) {
        chatRoom.getChildren().setAll(content);
    }

    @Override
    public void reset() {
        viewModel.reset();
    }

    @FXML
    private void onEnter(Event event) {
        sendMessage();
    }

    @FXML
    private void sendMessage() {
        viewModel.sendMessage();
    }

    @FXML
    private void backButton() {
        viewModel.exitChat();
        if (viewModel.isAdmin()) {
            getViewHandler().openView(View.ADMINMESSAGELIST);
        } else {
            getViewHandler().openView(View.DASHBOARD);
        }
    }
}
