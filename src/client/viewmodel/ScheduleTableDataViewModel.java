package client.viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import server.model.domain.user.Schedule;

public class ScheduleTableDataViewModel {
    private StringProperty from;
    private StringProperty to;
    private StringProperty shift;
    
    public ScheduleTableDataViewModel(Schedule schedule) {
        from = new SimpleStringProperty(schedule.getDateFrom().toString());
        to = new SimpleStringProperty(schedule.getDateTo().toString());
        shift = new SimpleStringProperty(schedule.getShift().getTimeFrom().toString() + " - " + schedule.getShift().getTimeTo().toString());
    }
    
    public StringProperty getFromProperty() {
        return from;
    }
    
    public StringProperty getToProperty() {
        return to;
    }
    
    public StringProperty getShiftProperty() {
        return shift;
    }
}
