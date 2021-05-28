package client.viewmodel;

import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

public interface MyScheduleViewModelInterface {

    void reset();
    StringProperty getErrorProperty();
    ObservableList<ScheduleTableDataViewModel> getSchedules();
}
