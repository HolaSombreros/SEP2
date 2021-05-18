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
    if (viewModel.setToNurse())
      getViewHandler().openView(View.PERSONALDATA);
  }

  @FXML private void adminButton() {
    if (viewModel.setToAdmin())
      getViewHandler().openView(View.PERSONALDATA);
  }

  @FXML private void back() {
    getViewHandler().openView(View.PERSONALDATA);
  }
}

