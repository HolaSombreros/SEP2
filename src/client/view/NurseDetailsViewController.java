package client.view;

import client.viewmodel.NurseDetailsViewModelInterface;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class NurseDetailsViewController extends ViewController
{
  private NurseDetailsViewModelInterface viewModel;
  @FXML private Label nameLabel;
  @FXML private Label cprLabel;
  @FXML private Label idLabel;
  @FXML private Label phoneLabel;
  @FXML private Label emailLabel;
  @FXML private DatePicker weekSelector;
  @FXML private RadioButton shift0;
  @FXML private RadioButton shift1;
  @FXML private RadioButton shift2;
  @FXML private Label errorLabel;

  public NurseDetailsViewController()
  {

  }

  @Override protected void init()
  {
    viewModel = getViewModelFactory().getNurseDetailsViewModel();
    nameLabel.textProperty().bind(viewModel.getNameProperty());
    cprLabel.textProperty().bind(viewModel.getCprProperty());
    idLabel.textProperty().bind(viewModel.getIdProperty());
    phoneLabel.textProperty().bind(viewModel.getPhoneProperty());
    emailLabel.textProperty().bind(viewModel.getEmailProperty());
    weekSelector.valueProperty().bindBidirectional(viewModel.getDateProperty());
    weekSelector.valueProperty().addListener((obs, oldVal, newVal) -> viewModel.loadShift());
    viewModel.disableDays(weekSelector);
    shift0.selectedProperty().bindBidirectional(viewModel.getShift0());
    shift1.selectedProperty().bindBidirectional(viewModel.getShift1());
    shift2.selectedProperty().bindBidirectional(viewModel.getShift2());
    ToggleGroup shift = new ToggleGroup();
    shift0.setToggleGroup(shift);
    shift1.setToggleGroup(shift);
    shift2.setToggleGroup(shift);
    errorLabel.textProperty().bind(viewModel.getErrorProperty());
    reset();
  }

  @Override public void reset()
  {
    viewModel.reset();
  }

  @FXML private void confirm()
  {
    viewModel.confirm();
  }

  @FXML private void back()
  {
    viewModel.back();
    getViewHandler().openView(View.USERLIST);
  }
}
