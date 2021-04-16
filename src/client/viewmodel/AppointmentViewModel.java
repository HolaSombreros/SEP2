package client.viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class AppointmentViewModel
{
    private StringProperty dateProperty;
    private StringProperty timeProperty;
    private StringProperty typeProperty;
    private StringProperty resultProperty;

    public AppointmentViewModel(Appointment appointment){
        dateProperty = new SimpleStringProperty(appointment.getDate());
        timeProperty = new SimpleStringProperty(appointment.getTime());
        typeProperty = new SimpleStringProperty(appointment.getType());
        resultProperty = new SimpleStringProperty(appointment.getResult());

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
}
