package client.viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import server.model.Appointment;

public class AppointmentTableViewModel
{
    private StringProperty dateProperty;
    private StringProperty timeProperty;
    private StringProperty typeProperty;
    private StringProperty resultProperty;

    public AppointmentTableViewModel(Appointment appointment){
        dateProperty = new SimpleStringProperty(appointment.getDate().toString());
        timeProperty = new SimpleStringProperty(appointment.getTimeInterval().toString());
        typeProperty = new SimpleStringProperty(appointment.getType().toString());
        resultProperty = new SimpleStringProperty(appointment.getStatus().toString());

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
