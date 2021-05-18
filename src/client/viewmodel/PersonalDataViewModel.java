package client.viewmodel;

import client.model.Model;
import javafx.beans.property.*;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import server.model.domain.user.*;
import java.util.Optional;

public class PersonalDataViewModel implements PersonalDataViewModelInterface
{
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
    private BooleanProperty removeButton;
    private StringProperty title;

    private Model model;
    private ViewState viewState;

    public PersonalDataViewModel(Model model, ViewState viewState){
        this.model = model;
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
        this.changeRole = new SimpleBooleanProperty(false);
        this.removeButton = new SimpleBooleanProperty(false);
        this.title = new SimpleStringProperty("My Personal Data");
    }
    @Override
    public void reset() {
        errorLabel.set("");
        loadInformation();

    }
    private void loadInformation(){
        if(viewState.getAdmin() != null){
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
            vaccineStatus.set(((Patient)viewState.getSelectedUser()).getVaccineStatus().toString());
            if(viewState.getAdmin().getCpr().equals(viewState.getSelectedUser().getCpr())){
                approveButton.set(true);
                declineButton.set(true);
                removeButton.set(true);
                errorLabel.set("Cannot approve vaccine for self");
            }
            else if(!vaccineStatus.get().equals("Pending")){
                approveButton.set(true);
                declineButton.set(true);
            }
            else{
                approveButton.set(false);
                declineButton.set(false);
                removeButton.set(false);
            }
        }
        else{
            password.set(viewState.getUser().getPassword());
            firstName.set(viewState.getUser().getFirstName());
            middleName.set(viewState.getUser().getMiddleName());
            lastName.set(viewState.getUser().getLastName());
            cpr.set(viewState.getUser().getCpr());
            number.set(viewState.getUser().getAddress().getNumber());
            phoneNumber.set(viewState.getUser().getPhone());
            zipCode.set(viewState.getUser().getAddress().getZipcode());
            email.set(viewState.getUser().getEmail());
            street.set(viewState.getUser().getAddress().getStreet());
            vaccineStatus.set(viewState.getPatient().getVaccineStatus().toString());
        }
    }

    @Override
    public void editDetails() {
        errorLabel.set("");
        if(confirmEditingType(1)){
           try{
               User user = model.editUserInformation(viewState.getUser(), password.get(), firstName.get(), middleName.get(), lastName.get(), phoneNumber.get(), email.get(), street.get(),number.get(), zipCode.get());
               viewState.setUser(user);
               errorLabel.set("Changes were saved");
           }
           catch (Exception e){
               errorLabel.set(e.getMessage());
           }
        }
        else
            reset();
    }
/*
    @Override
    public boolean removeUser() {
        if(confirmEditingType(2)) {
            try {
                model.removeUser(viewState.getSelectedUser());
                errorLabel.set("User was removed");
                return true;
            }
            catch (Exception e) {
                errorLabel.set(e.getMessage());
            }
        }
        return false;
    }
*/

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
        switch (criteria)
        {
            case 1:
                if ((viewState.getUser() instanceof Administrator)) {
                    String addForAdmin = "Vaccine Status: " + ((Patient) viewState.getSelectedUser()).getVaccineStatus().toString() + "\n";
                    if (middleName.get() != null)
                        alert.setHeaderText(adminHeader + secondHeader + middleHeader + thirdPart + addForAdmin);
                    else
                        alert.setHeaderText(adminHeader + secondHeader + thirdPart + addForAdmin);
                }
                else {
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

    public boolean isLoggedInAsNurse() {
        return viewState.getUser() instanceof Nurse;
    }

    @Override
    public StringProperty getFirstNameProperty()
    {
        return firstName;
    }

    @Override
    public StringProperty getMiddleNameProperty()
    {
        return middleName;
    }

    @Override
    public StringProperty getLastNameProperty()
    {
        return lastName;
    }

    @Override
    public StringProperty getStreetProperty()
    {
        return street;
    }

    @Override
    public StringProperty getNumberProperty()
    {
        return number;
    }

    @Override
    public StringProperty getCprProperty()
    {
        return cpr;
    }

    @Override
    public StringProperty getEmailProperty()
    {
        return email;
    }

    @Override
    public StringProperty getPhoneNumberProperty()
    {
        return phoneNumber;
    }

    @Override
    public StringProperty getErrorLabelProperty()
    {
        return errorLabel;
    }
    @Override
    public StringProperty getPasswordProperty(){
        return password;
    }
    @Override
    public IntegerProperty getZipCodeProperty(){
        return zipCode;
    }

    @Override
    public StringProperty getVaccineStatusProperty()
    {
        return vaccineStatus;
    }

    public BooleanProperty approveButtonProperty() {
        return approveButton;
    }

    public BooleanProperty declineButtonProperty() {
        return declineButton;
    }

    @Override public BooleanProperty changeRoleProperty()
    {
        return changeRole;
    }

    @Override
    public BooleanProperty removeButtonProperty()
    {
        return removeButton;
    }

    public StringProperty titleProperty() {
        return title;
    }

    public boolean isAdmin(){
        return  viewState.getAdmin() != null;
    }


    public void approve(){
        ((Patient) viewState.getSelectedUser()).getVaccineStatus().approve((Patient) viewState.getSelectedUser());
        if(confirmEditingType(1)) {
            model.updateVaccineStatus(((Patient) viewState.getSelectedUser()));
            vaccineStatus.set(((Patient) viewState.getSelectedUser()).getVaccineStatus().toString());
            approveButton.set(true);
            declineButton.set(true);
            errorLabel.set("Patient was " + ((Patient) viewState.getSelectedUser()).getVaccineStatus().toString() + " for vaccination");
        }else
            ((Patient) viewState.getSelectedUser()).setVaccineStatus(new PendingStatus());
    }

    public void decline(){
        ((Patient) viewState.getSelectedUser()).getVaccineStatus().decline((Patient) viewState.getSelectedUser());
        if(confirmEditingType(1)){
            model.updateVaccineStatus(((Patient) viewState.getSelectedUser()));
            vaccineStatus.set(((Patient) viewState.getSelectedUser()).getVaccineStatus().toString());
            approveButton.set(true);
            declineButton.set(true);
            errorLabel.set("Patient was " + ((Patient) viewState.getSelectedUser()).getVaccineStatus().toString() + " for vaccination");
        }
        else
            ((Patient) viewState.getSelectedUser()).getVaccineStatus().apply((Patient) viewState.getSelectedUser());

    }

    @Override public boolean changeRole() {
        if (viewState.getSelectedUser() instanceof Nurse || viewState.getSelectedUser() instanceof Administrator) {
            errorLabel.set("The User already has a role");
            return false;
        }
        else return true;
    }
}
