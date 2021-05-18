package client.view;

import client.viewmodel.SetRoleViewModelInterface;
import javafx.fxml.FXML;

public class SetRoleViewController extends ViewController
{
  private SetRoleViewModelInterface viewModel;

  public SetRoleViewController() { }

  @Override protected void init() {
    viewModel = getViewModelFactory().getSetRoleViewModel();
  }

  @Override public void reset() { }

  @FXML private void nurseButton() {
    viewModel.setToNurse();
  }

  @FXML private void adminButton() {
    viewModel.setToAdmin();
  }
}

