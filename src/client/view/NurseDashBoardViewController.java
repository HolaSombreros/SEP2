package client.view;

import client.viewmodel.AppointmentTableViewModel;
import client.viewmodel.NurseDashBoardViewModelInterface;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class NurseDashBoardViewController extends ViewController {
    private NurseDashBoardViewModelInterface viewModel;
    @FXML private Label usernameLabel;
    @FXML private Label roleLabel;
    @FXML private Label timeLabel;
    @FXML private Label dateLabel;
    @FXML private TextField searchBar;
    @FXML private CheckBox showFinishedAppointments;
    @FXML private Label errorLabel;
    @FXML private Button filterButton;
    
    @FXML private TableView<AppointmentTableViewModel> appointmentTable;
    @FXML private TableColumn<AppointmentTableViewModel, String> cprColumn;
    @FXML private TableColumn<AppointmentTableViewModel, String> nameColumn;
    @FXML private TableColumn<AppointmentTableViewModel, String> dateColumn;
    @FXML private TableColumn<AppointmentTableViewModel, String> timeColumn;
    @FXML private TableColumn<AppointmentTableViewModel, String> typeColumn;
    @FXML private TableColumn<AppointmentTableViewModel, String> resultColumn;
    
    @Override
    protected void init() {
        viewModel = getViewModelFactory().getNurseDashBoardViewModel();
        usernameLabel.textProperty().bind(viewModel.getUsernameProperty());
        roleLabel.textProperty().bind(viewModel.getRoleProperty());
        timeLabel.textProperty().bind(viewModel.getTimeProperty());
        dateLabel.textProperty().bind(viewModel.getDateProperty());
        searchBar.textProperty().bindBidirectional(viewModel.getSearchBarProperty());
        showFinishedAppointments.selectedProperty().bindBidirectional(viewModel.showFinishedAppointmentsProperty());
        errorLabel.textProperty().bind(viewModel.getErrorProperty());
        errorLabel.textFillProperty().bind(viewModel.getErrorFillProperty());
        filterButton.textProperty().bind(viewModel.getFilterButtonTextProperty());
        
        appointmentTable.setItems(viewModel.getAppointments());
        cprColumn.setCellValueFactory(data -> data.getValue().getCprProperty());
        nameColumn.setCellValueFactory(data -> data.getValue().getFullNameProperty());
        dateColumn.setCellValueFactory(data -> data.getValue().getDateProperty());
        timeColumn.setCellValueFactory(data -> data.getValue().getTimeProperty());
        typeColumn.setCellValueFactory(data -> data.getValue().getTypeProperty());
        resultColumn.setCellValueFactory(data -> data.getValue().getResultProperty());
        
        appointmentTable.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> viewModel.setSelectedAppointment(newVal));
        
        reset();
    }
    
    @Override
    public void reset() {
        viewModel.reset();
        appointmentTable.getSelectionModel().clearSelection();
    }
    
    @FXML
    private void filterTable() {
        // update table
        viewModel.filterTable();
    }
    
    @FXML
    private void filterTableButton() {
        // update button text
        viewModel.filterTable();
    }
    
    @FXML
    private void personalData() {
        // open view
    }
    
    @FXML
    private void details() {
        if (viewModel.updateViewState()) {
            // open view
        }
    }
    
    @FXML
    private void logout() {
        viewModel.logout();
        getViewHandler().openView(View.LOGIN);
    }
}
