package client.viewmodel;

import client.model.Model;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class SetRoleViewModel implements SetRoleViewModelInterface
{
  private Model model;
  private ViewState viewState;

  public SetRoleViewModel(Model model, ViewState viewState) {
    this.model = model;
    this.viewState = viewState;
  }

  @Override public boolean setToNurse() {
    if (confirmEditing("Nurse")) {
      model.setRole(viewState.getSelectedUser(),"Nurse");
      return true;
    }
    return false;
  }

  @Override public boolean setToAdmin() {
    if (confirmEditing("Administrator")) {
      model.setRole(viewState.getSelectedUser(), "Administrator");
      return true;
    }
    return false;
  }

  private boolean confirmEditing(String role) {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Confirm editing");
    alert.setHeaderText("Are you sure you want to change the user's role? \n\n" + "Role: " + role);
    Optional<ButtonType> result = alert.showAndWait();
    return result.isPresent() && result.get() == ButtonType.OK;
  }
}
