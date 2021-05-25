package client.view;

import client.viewmodel.PatientChatViewModelInterface;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;



public class PatientChatViewController extends ViewController {

    @FXML private TextArea textArea;
    @FXML private Label errorLabel;
    @FXML private ScrollPane scrollPane;
    @FXML private VBox chatRoom;
    @FXML private Label user;

    private PatientChatViewModelInterface viewModel;

    @Override
    protected void init() {
        viewModel = getViewModelFactory().getPatientChatViewModel();
        textArea.textProperty().bindBidirectional(viewModel.getTextAreaProperty());
        errorLabel.textProperty().bind(viewModel.getErrorLabelProperty());
        user.textProperty().bind(viewModel.getUserProperty());
        scrollPane.vvalueProperty().bind(chatRoom.heightProperty());
        Bindings.bindContentBidirectional(viewModel.getMessages(), chatRoom.getChildren());
        reset();
    }

    @Override
    public void reset() {
        viewModel.reset();
    }
    @FXML private void onEnter(){
        sendMessage();
    }

    @FXML
    private void sendMessage(){
        viewModel.sendMessage();
    }

    @FXML
    private void backButton(){
        getViewHandler().openView(View.DASHBOARD);
    }
}
