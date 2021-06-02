package client.viewmodel.user;

import client.model.UserModel;
import client.viewmodel.ViewState;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class SetRoleViewModel implements SetRoleViewModelInterface {
    private UserModel userModel;
    private ViewState viewState;

    public SetRoleViewModel(UserModel userModel, ViewState viewState) {
        this.userModel = userModel;
        this.viewState = viewState;
    }

    @Override
    public boolean setToNurse() {
        if (confirmEditing("Nurse")) {
            userModel.setRole(viewState.getSelectedUser(), "Nurse");
            return true;
        }
        return false;
    }

    @Override
    public boolean setToAdmin() {
        if (confirmEditing("Administrator")) {
            userModel.setRole(viewState.getSelectedUser(), "Administrator");
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
