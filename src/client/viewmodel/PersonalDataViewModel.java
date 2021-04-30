package client.viewmodel;

import client.model.Model;
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
    private StringProperty city;
    private StringProperty cpr;
    private StringProperty email;
    private StringProperty phoneNumber;
    private StringProperty errorLabel;

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
        this.city = new SimpleStringProperty();
        this.cpr = new SimpleStringProperty();
        this.email = new SimpleStringProperty();
        this.phoneNumber = new SimpleStringProperty();
        this.errorLabel = new SimpleStringProperty();
    }
    @Override
    public void reset()
    {
        errorLabel.set("");
        loadInformation();
    }
    public void loadInformation(){
        firstName.set(viewState.getUser().getFirstName());
        middleName.set(viewState.getUser().getMiddleName());
        lastName.set(viewState.getUser().getLastName());
        cpr.set(viewState.getUser().getCpr());
        number.set(viewState.getUser().getAddress().getNumber());
        phoneNumber.set(viewState.getUser().getPhone());
        email.set(viewState.getUser().getEmail());
        city.set(viewState.getUser().getAddress().getCity());
        street.set(viewState.getUser().getAddress().getStreet());
    }

    @Override
    public void editDetails()
    {
        errorLabel.set("");
        if(confirmEditing()){

        }

    }

    @Override
    public boolean confirmEditing()
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm editing");
        if(middleName.get() == null){
            alert.setHeaderText("Are you sure you want to edit your personal information? \n\n" +
                    "First Name: " + firstName.get() + "\n" +
                    "Last Name: " + lastName.get() + "\n" +
                    "CPR: " + cpr.get() + "\n" +
                    "Email: " + email.get() + "\n" +
                    "Phone Number: " + phoneNumber.get() + "\n" +
                    "Street: " + street.get() + "\n" +
                    "Number: " + number.get() + "\n" +
                    "City: " + city.get());
        }
        else
        alert.setHeaderText("Are you sure you want to edit your personal information? \n\n" +
                "First Name: " + firstName.get() + "\n" +
                "Middle Name: " + middleName.get() + "\n"+
                "Last Name: " + lastName.get() + "\n" +
                "CPR: " + cpr.get() + "\n" +
                "Email: " + email.get() + "\n" +
                "Phone Number: " + phoneNumber.get() + "\n" +
                "Street: " + street.get() + "\n" +
                "Number: " + number.get() + "\n" +
                "City: " + city.get());
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
    public StringProperty getCity()
    {
        return city;
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


}
