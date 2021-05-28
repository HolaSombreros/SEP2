package client.view;

import client.viewmodel.MyScheduleViewModelInterface;
import client.viewmodel.ScheduleTableDataViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class MyScheduleViewController extends ViewController {
    private MyScheduleViewModelInterface viewModel;
    
    @FXML private TableView<ScheduleTableDataViewModel> table;
    @FXML private TableColumn<ScheduleTableDataViewModel, String> fromColumn;
    @FXML private TableColumn<ScheduleTableDataViewModel, String> toColumn;
    @FXML private TableColumn<ScheduleTableDataViewModel, String> shiftColumn;
    @FXML private Label errorLabel;

    
    @Override
    protected void init() {
        viewModel = getViewModelFactory().getMyScheduleViewModel();
        
        table.setItems(viewModel.getSchedules());
        fromColumn.setCellValueFactory(data -> data.getValue().getFromProperty());
        toColumn.setCellValueFactory(data -> data.getValue().getToProperty());
        shiftColumn.setCellValueFactory(data -> data.getValue().getShiftProperty());
        errorLabel.textProperty().bind(viewModel.getErrorProperty());
        reset();
    }
    
    @Override
    public void reset() {
        table.getSelectionModel().clearSelection();
        viewModel.reset();
    }
    
    @FXML
    private void back() {
        getViewHandler().openView(View.NURSEDASHBOARD);
    }
}
