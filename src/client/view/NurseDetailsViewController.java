package client.view;

import client.viewmodel.NurseDetailsViewModelInterface;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class NurseDetailsViewController extends ViewController
{
  private NurseDetailsViewModelInterface viewModel;
  @FXML private Label nameLabel;
  @FXML private Label cprLabel;
  @FXML private Label idLabel;
  @FXML private Label phoneLabel;
  @FXML private Label emailLabel;
  @FXML private DatePicker daySelector;
  @FXML private TextField fromField;
  @FXML private TextField toField;
  @FXML private Label errorLabel;

  public NurseDetailsViewController() {}

  @Override protected void init()
  {

  }

  @Override public void reset()
  {

  }

  @FXML private void confirm()
  {

  }

  @FXML private void back()
  {
    viewModel.back();
    getViewHandler().openView(View.USERLIST);
  }
}
