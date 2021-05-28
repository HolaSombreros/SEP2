package client.view.appointment;

import client.view.View;
import client.view.ViewController;
import client.viewmodel.appointment.AppointmentListViewModelInterface;
import client.viewmodel.appointment.AppointmentTableViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class AppointmentListViewController extends ViewController
{
    @FXML
    private TableView<AppointmentTableViewModel> appointmentTable;
    @FXML
    private TableColumn<AppointmentTableViewModel, String> dateColumn;
    @FXML
    private TableColumn<AppointmentTableViewModel, String> statusColumn;
    @FXML
    private TableColumn<AppointmentTableViewModel, String> timeColumn;
    @FXML
    private TableColumn<AppointmentTableViewModel, String> typeColumn;
    @FXML
    private Label errorLabel;

    private AppointmentListViewModelInterface viewModel;

    public AppointmentListViewController() {

    }

    @Override
    protected void init() {
        viewModel = getViewModelFactory().getAppointmentListViewModel();
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().getDateProperty());
        statusColumn.setCellValueFactory(cellData -> cellData.getValue().getStatusProperty());
        timeColumn.setCellValueFactory(cellData -> cellData.getValue().getTimeProperty());
        typeColumn.setCellValueFactory(cellData -> cellData.getValue().getTypeProperty());
        errorLabel.textProperty().bind(viewModel.getErrorProperty());
        appointmentTable.setItems(viewModel.getAppointments());
        appointmentTable.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> viewModel.setSelectedAppointment(newValue));
        reset();
    }

    @FXML
    private void seeDetails() {
        boolean openWindow = viewModel.seeDetails();
        if (openWindow)
            getViewHandler().openView(View.APPOINTMENTDETAILS);
    }

    @FXML
    private void bookAppointment() {
        getViewHandler().openView(View.ADDAPPOINTMENT);
    }

    @FXML
    private void backButton() {
        getViewHandler().openView(View.DASHBOARD);
    }

    @Override
    public void reset() {
        viewModel.reset();
    }
}
