package client.viewmodel;

import client.model.Model;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;


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
    }
    @Override
    public void reset()
    {
        loadInformation();
        errorLabel.set("");

    }
    public void loadInformation(){
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
               model.editUserInformation(viewState.getUser(), password.get(), firstName.get(), middleName.get(), lastName.get(), phoneNumber.get(), email.get(), street.get(),number.get(), zipCode.get());
               errorLabel.set("Changes were saved");
           }
           catch (Exception e){
               errorLabel.set(e.getMessage());
           }
        }
        else
            reset();
    }

    @Override
    public boolean confirmEditing()
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
    public StringProperty getFirstName()
    {
        return firstName;
    }

    @Override
    public StringProperty getMiddleName()
    {
        return middleName;
    }

    @Override
    public StringProperty getLastName()
    {
        return lastName;
    }

    @Override
    public StringProperty getStreet()
    {
        return street;
    }

    @Override
    public StringProperty getNumber()
    {
        return number;
    }


    @Override
    public StringProperty getCpr()
    {
        return cpr;
    }

    @Override
    public StringProperty getEmail()
    {
        return email;
    }

    @Override
    public StringProperty getPhoneNumber()
    {
        return phoneNumber;
    }

    @Override
    public StringProperty getErrorLabel()
    {
        return errorLabel;
    }
    @Override
    public StringProperty getPassword(){
        return password;
    }
    @Override
    public IntegerProperty getZipCode(){
        return zipCode;
    }


}
