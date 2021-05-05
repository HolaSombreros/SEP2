package client.viewmodel;

import client.model.Model;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import server.model.domain.user.User;


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
    }
    @Override
    public void reset()
    {
        loadInformation();
        errorLabel.set("");

    }
    private void loadInformation(){
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
    }

    @Override
    public void editDetails()
    {
        errorLabel.set("");
        if(confirmEditing()){
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

    private boolean confirmEditing()
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm editing");
        if(middleName.get() == null){
            alert.setHeaderText("Are you sure you want to edit your personal information? \n\n" +
                    "Password: " + password.get() + "\n" +
                    "First Name: " + firstName.get() + "\n" +
                    "Last Name: " + lastName.get() + "\n" +
                    "CPR: " + cpr.get() + "\n" +
                    "Email: " + email.get() + "\n" +
                    "Phone Number: " + phoneNumber.get() + "\n" +
                    "Street: " + street.get() + "\n" +
                    "Number: " + number.get() + "\n");
        }
        else{
        alert.setHeaderText("Are you sure you want to edit your personal information? \n\n" +
                "Password: " + password.get() + "\n" +
                "First Name: " + firstName.get() + "\n" +
                "Middle Name: " + middleName.get() + "\n"+
                "Last Name: " + lastName.get() + "\n" +
                "CPR: " + cpr.get() + "\n" +
                "Email: " + email.get() + "\n" +
                "Phone Number: " + phoneNumber.get() + "\n" +
                "Street: " + street.get() + "\n" +
                "Number: " + number.get() + "\n" );
        }
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
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


}
