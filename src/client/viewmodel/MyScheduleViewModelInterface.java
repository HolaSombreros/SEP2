package client.viewmodel;

import javafx.collections.ObservableList;

public interface MyScheduleViewModelInterface {
    void reset();
    ObservableList<ScheduleTableDataViewModel> getSchedules();
}
