package client.viewmodel;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import server.model.Appointment;
import server.model.AppointmentTime;

public class AppointmentTableViewModel
{
    private StringProperty dateProperty;
    private StringProperty timeProperty;
    private StringProperty typeProperty;
    private StringProperty resultProperty;
    private IntegerProperty idProperty;


    public AppointmentTableViewModel(Appointment appointment){
        dateProperty = new SimpleStringProperty(appointment.getDate().toString());
        timeProperty = new SimpleStringProperty(appointment.getTimeInterval().toString());
        typeProperty = new SimpleStringProperty(appointment.getAppointmentList().getType().toString());
        resultProperty = new SimpleStringProperty(appointment.getStatus().toString());
        idProperty = new SimpleIntegerProperty(appointment.getId());

    }
    public StringProperty datePropertyProperty()
    {
        return dateProperty;
    }

    public StringProperty timePropertyProperty()
    {
        return timeProperty;
    }

    public StringProperty typePropertyProperty()
    {
        return typeProperty;
    }

    public StringProperty resultPropertyProperty()
    {
        return resultProperty;
    }

    public IntegerProperty  getIdProperty(){
        return idProperty;
    }

}
