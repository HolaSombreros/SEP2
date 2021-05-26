package client.view;

import client.viewmodel.AdminMessageListViewModelInterface;
import client.viewmodel.MessageTableDataViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class AdminMessageListViewController extends ViewController {
    private AdminMessageListViewModelInterface viewModel;
    
    @FXML private TableView<MessageTableDataViewModel> table;
    @FXML private TableColumn<MessageTableDataViewModel, String> cprColumn;
    @FXML private TableColumn<MessageTableDataViewModel, String> nameColumn;
    @FXML private TableColumn<MessageTableDataViewModel, String> dateColumn;
    @FXML private TableColumn<MessageTableDataViewModel, String> statusColumn;
    @FXML private CheckBox showReadMessages;
    @FXML private Label errorLabel;
    
    @Override
    protected void init() {
        viewModel = getViewModelFactory().getAdminMessageListViewModel();
        
        table.setItems(viewModel.getTableData());
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> viewModel.setSelectedChat(newVal));
        cprColumn.setCellValueFactory(data -> data.getValue().getCprProperty());
        nameColumn.setCellValueFactory(data -> data.getValue().getNameProperty());
        dateColumn.setCellValueFactory(data -> data.getValue().getDateProperty());
        statusColumn.setCellValueFactory(data -> data.getValue().getStatusProperty());
        showReadMessages.selectedProperty().bindBidirectional(viewModel.showReadMessagesProperty());
        errorLabel.textProperty().bind(viewModel.getErrorProperty());
        errorLabel.textFillProperty().bind(viewModel.getErrorFillProperty());
    }
    
    @Override
    public void reset() {
        table.getSelectionModel().clearSelection();
        viewModel.reset();
    }
    
    @FXML
    private void enterChat() {
        if (viewModel.enterChat()) {
            getViewHandler().openView(View.PATIENTCHAT);
        }
    }
    
    @FXML
    private void back() {
        getViewHandler().openView(View.USERLIST);
    }
}
