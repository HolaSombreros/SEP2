package client.view;

import client.viewmodel.NurseDetailsViewModelInterface;
import javafx.beans.binding.Bindings;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import util.IntStringConverter;

public class NurseDetailsViewController extends ViewController
{
  private NurseDetailsViewModelInterface viewModel;
  @FXML private Label nameLabel;
  @FXML private Label cprLabel;
  @FXML private Label idLabel;
  @FXML private Label phoneLabel;
  @FXML private Label emailLabel;
  @FXML private DatePicker daySelector;
  @FXML private TextField fromHour;
  @FXML private TextField fromMinute;
  @FXML private TextField toHour;
  @FXML private TextField toMinute;
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
    daySelector.valueProperty().bindBidirectional(viewModel.getDateProperty());
    Bindings.bindBidirectional(fromHour.textProperty(), viewModel.getFromHourProperty(), new IntStringConverter());
    Bindings.bindBidirectional(fromMinute.textProperty(), viewModel.getFromMinuteProperty(), new IntStringConverter());
    Bindings.bindBidirectional(toHour.textProperty(), viewModel.getToHourProperty(), new IntStringConverter());
    Bindings.bindBidirectional(toMinute.textProperty(), viewModel.getToMinuteProperty(), new IntStringConverter());
    daySelector.valueProperty().addListener((obs, oldVal, newVal) -> viewModel.loadTimeInterval());
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

  @FXML private void onEnter(Event event)
  {
    if (event.getSource() == fromHour)
    {
      fromMinute.requestFocus();
    }
    else if (event.getSource() == fromMinute)
    {
      toHour.requestFocus();
    }
    else if (event.getSource() == toHour)
    {
      toMinute.requestFocus();
    }
  }
}
