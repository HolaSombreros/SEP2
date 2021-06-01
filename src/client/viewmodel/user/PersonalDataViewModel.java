package client.viewmodel.user;

import client.model.UserModel;
import client.viewmodel.ViewState;
import javafx.beans.property.*;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import server.model.domain.user.*;

import java.util.Optional;

public class PersonalDataViewModel implements PersonalDataViewModelInterface {
    private StringProperty firstName;
    private StringProperty middleName;
    private StringProperty lastName;
    private StringProperty street;
    private StringProperty number;
    private StringProperty cpr;
    private StringProperty email;
    private StringProperty phoneNumber;
    private StringProperty password;
    private StringProperty errorLabel;
    private IntegerProperty zipCode;
    private StringProperty vaccineStatus;
    private BooleanProperty approveButton;
    private BooleanProperty declineButton;
    private BooleanProperty changeRole;
    private BooleanProperty vaccineLabelVisibility;
    private BooleanProperty vaccineStatusVisibility;
    private StringProperty title;

    private UserModel userModel;
    private ViewState viewState;

    public PersonalDataViewModel(UserModel userModel, ViewState viewState) {
        this.userModel = userModel;
        this.viewState = viewState;
        this.firstName = new SimpleStringProperty();
        this.middleName = new SimpleStringProperty();
        this.lastName = new SimpleStringProperty();
        this.street = new SimpleStringProperty();
        this.number = new SimpleStringProperty();
        this.cpr = new SimpleStringProperty();
        this.email = new SimpleStringProperty();
        this.phoneNumber = new SimpleStringProperty();
        this.password = new SimpleStringProperty();
        this.zipCode = new SimpleIntegerProperty();
        this.errorLabel = new SimpleStringProperty();
        this.vaccineStatus = new SimpleStringProperty();
        this.approveButton = new SimpleBooleanProperty(false);
        this.declineButton = new SimpleBooleanProperty(false);
        this.changeRole = new SimpleBooleanProperty(isAdmin());
        this.vaccineLabelVisibility = new SimpleBooleanProperty(true);
        this.vaccineStatusVisibility =  new SimpleBooleanProperty(true);
        this.title = new SimpleStringProperty();
    }

    @Override
    public void reset() {
        errorLabel.set("");
        title.set("");
        loadInformation();
    }

    private void loadInformation() {
        if (viewState.getUser() instanceof Administrator) {
            title.set("Patient");
            password.set(viewState.getSelectedUser().getPassword());
            firstName.set(viewState.getSelectedUser().getFirstName());
            middleName.set(viewState.getSelectedUser().getMiddleName());
            lastName.set(viewState.getSelectedUser().getLastName());
            cpr.set(viewState.getSelectedUser().getCpr());
            number.set(viewState.getSelectedUser().getAddress().getNumber());
            phoneNumber.set(viewState.getSelectedUser().getPhone());
            zipCode.set(viewState.getSelectedUser().getAddress().getZipcode());
            email.set(viewState.getSelectedUser().getEmail());
            street.set(viewState.getSelectedUser().getAddress().getStreet());
            changeRole.set(true);
            vaccineStatus.set(((Patient) viewState.getSelectedUser()).getVaccineStatus().toString());
            if ((viewState.getUser()).getCpr().equals(viewState.getSelectedUser().getCpr())) {
                vaccineLabelVisibility.set(true);
                approveButton.set(false);
                declineButton.set(false);
            }
            else if (vaccineStatus.get().equals("Pending")) {
                vaccineStatusVisibility.set(true);
                vaccineLabelVisibility.set(true);
                approveButton.set(true);
                declineButton.set(true);
            }
            else {
                vaccineStatusVisibility.set(true);
                vaccineLabelVisibility.set(true);
                approveButton.set(false);
                declineButton.set(false);
            }
        }
        else {
            title.set("My personal data");
            User user = viewState.getUser();
            password.set(user.getPassword());
            firstName.set(user.getFirstName());
            middleName.set(user.getMiddleName());
            lastName.set(user.getLastName());
            cpr.set(user.getCpr());
            number.set(user.getAddress().getNumber());
            phoneNumber.set(user.getPhone());
            zipCode.set(user.getAddress().getZipcode());
            email.set(user.getEmail());
            street.set(user.getAddress().getStreet());
            if(user instanceof Patient) {
                vaccineLabelVisibility.set(true);
                vaccineStatusVisibility.set(true);
                vaccineStatus.set(((Patient) viewState.getUser()).getVaccineStatus().toString());
                changeRole.set(false);
                approveButton.set(false);
                declineButton.set(false);
            }
            else {
                vaccineLabelVisibility.set(false);
                vaccineStatusVisibility.set(false);
            }
        }
    }

    @Override
    public void editDetails() {
        errorLabel.set("");
        if (confirmEditingType(1)) {
            try {
                if (viewState.getUser() instanceof Administrator) {
                    User user = userModel.editUserInformation(viewState.getSelectedUser(), password.get(), firstName.get(), middleName.get(), lastName.get(), phoneNumber.get(), email.get(), street.get(), number.get(), zipCode.get());
                    viewState.setSelectedUser(user);
                } else {
                    User user = userModel.editUserInformation( viewState.getUser(), password.get(), firstName.get(), middleName.get(), lastName.get(), phoneNumber.get(), email.get(), street.get(), number.get(), zipCode.get());
                    if (viewState.getUser() instanceof Patient)
                        viewState.setUser(userModel.getPatient(user.getCpr()));
                    else viewState.setUser(userModel.getNurses().getUserByCpr(user.getCpr()));
                }
                errorLabel.set("Changes were saved");
            } catch (Exception e) {
                errorLabel.set(e.getMessage());
            }
        } else
            reset();
    }


    private boolean confirmEditingType(int criteria) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm editing");
        String userHeader = "Are you sure you want to edit your personal information? \n\n";
        String adminHeader = "Are you sure you want to edit user information? \n\n";
        String removeUser = "Are you sure you want to remove this user? \n\n";
        String secondHeader = "Password: " + password.get() + "\n" + "First Name: " + firstName.get() + "\n";
        String middleHeader = "Middle Name: " + middleName.get() + "\n";
        String thirdPart = "Last Name: " + lastName.get() + "\n" +
                "CPR: " + cpr.get() + "\n" +
                "Email: " + email.get() + "\n" +
                "Phone Number: " + phoneNumber.get() + "\n" +
                "Street: " + street.get() + "\n" +
                "Number: " + number.get() + "\n";
        switch (criteria) {
            case 1:
                if ((viewState.getUser() instanceof Administrator)) {
                    String addForAdmin = "Vaccine Status: " + ((Patient) viewState.getSelectedUser()).getVaccineStatus().toString() + "\n";
                    if (middleName.get() != null)
                        alert.setHeaderText(adminHeader + secondHeader + middleHeader + thirdPart + addForAdmin);
                    else
                        alert.setHeaderText(adminHeader + secondHeader + thirdPart + addForAdmin);
                } else {
                    if (middleName.get() != null)
                        alert.setHeaderText(userHeader + secondHeader + middleHeader + thirdPart);
                    else
                        alert.setHeaderText(userHeader + secondHeader + thirdPart);
                }
                break;
            case 2:
                if ((viewState.getUser() instanceof Administrator)) {
                    if (middleName.get() != null)
                        alert.setHeaderText(removeUser + secondHeader + middleHeader + thirdPart);
                    else
                        alert.setHeaderText(removeUser + secondHeader + thirdPart);
                }
                break;
        }
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    @Override
    public boolean isLoggedInAsNurse() {
        return viewState.getUser() instanceof Nurse;
    }

    @Override
    public StringProperty getFirstNameProperty() {
        return firstName;
    }

    @Override
    public StringProperty getMiddleNameProperty() {
        return middleName;
    }

    @Override
    public StringProperty getLastNameProperty() {
        return lastName;
    }

    @Override
    public StringProperty getStreetProperty() {
        return street;
    }

    @Override
    public StringProperty getNumberProperty() {
        return number;
    }

    @Override
    public StringProperty getCprProperty() {
        return cpr;
    }

    @Override
    public StringProperty getEmailProperty() {
        return email;
    }

    @Override
    public StringProperty getPhoneNumberProperty() {
        return phoneNumber;
    }

    @Override
    public StringProperty getErrorLabelProperty() {
        return errorLabel;
    }

    @Override
    public StringProperty getPasswordProperty() {
        return password;
    }

    @Override
    public IntegerProperty getZipCodeProperty() {
        return zipCode;
    }

    @Override
    public StringProperty getVaccineStatusProperty() {
        return vaccineStatus;
    }

    @Override
    public BooleanProperty approveButtonProperty() {
        return approveButton;
    }

    @Override
    public BooleanProperty declineButtonProperty() {
        return declineButton;
    }

    @Override
    public BooleanProperty changeRoleProperty() {
        return changeRole;
    }

    @Override
    public BooleanProperty vaccineLabelVisibilityProperty() {
        return vaccineLabelVisibility;
    }

    @Override
    public BooleanProperty vaccineStatusVisibilityProperty() {
        return vaccineStatusVisibility;
    }

    public StringProperty titleProperty() {
        return title;
    }

    @Override
    public void back() {
        viewState.removeSelectedUser();
    }

    public boolean isAdmin() {
        return viewState.getUser() instanceof Administrator;
    }

    @Override
    public void approve() {
        ((Patient) viewState.getSelectedUser()).getVaccineStatus().approve((Patient) viewState.getSelectedUser());
        if (confirmEditingType(1)) {
            userModel.updateVaccineStatus(((Patient) viewState.getSelectedUser()));
            vaccineStatus.set(((Patient) viewState.getSelectedUser()).getVaccineStatus().toString());
            approveButton.set(true);
            declineButton.set(true);
            errorLabel.set("Patient was " + ((Patient) viewState.getSelectedUser()).getVaccineStatus().toString() + " for vaccination");
        } else
            ((Patient) viewState.getSelectedUser()).setVaccineStatus(new PendingStatus());
    }

    @Override
    public void decline() {
        ((Patient) viewState.getSelectedUser()).getVaccineStatus().decline((Patient) viewState.getSelectedUser());
        if (confirmEditingType(1)) {
            userModel.updateVaccineStatus(((Patient) viewState.getSelectedUser()));
            vaccineStatus.set(((Patient) viewState.getSelectedUser()).getVaccineStatus().toString());
            approveButton.set(true);
            declineButton.set(true);
            errorLabel.set("Patient was " + ((Patient) viewState.getSelectedUser()).getVaccineStatus().toString() + " for vaccination");
        } else
            ((Patient) viewState.getSelectedUser()).getVaccineStatus().apply((Patient) viewState.getSelectedUser());
    }

    @Override
    public boolean changeRole() {
        if (userModel.getNurses().getUserByCpr(viewState.getSelectedUser().getCpr()) != null || userModel.getAdministrators().getUserByCpr(viewState.getSelectedUser().getCpr()) != null) {
            errorLabel.set("The User already has a role");
            return false;
        } else return true;
    }
}
