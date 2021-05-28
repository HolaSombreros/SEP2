package client.viewmodel;

import client.model.UserModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import server.model.domain.user.Schedule;


public class MyScheduleViewModel implements MyScheduleViewModelInterface {

    private UserModel userModel;
    private ObservableList<ScheduleTableDataViewModel> schedule;
    private ViewState viewState;
    private StringProperty error;

    public MyScheduleViewModel(UserModel userModel, ViewState viewState) {
        this.userModel = userModel;
        this.viewState = viewState;
        this.schedule = FXCollections.observableArrayList();
        this.error = new SimpleStringProperty();

    }

    @Override
    public void reset() {
        schedule.clear();
        error.set("");
        loadPersonalSchedule();
    }

    private void loadPersonalSchedule() {
        try {
            for(Schedule schedules : userModel.getNurses().getNurse(viewState.getUser().getCpr()).getScheduleList().getSchedules())
                schedule.add(new ScheduleTableDataViewModel(schedules));
        }
        catch (Exception e) {
            error.set(e.getMessage());
        }
    }

    @Override
    public StringProperty getErrorProperty() {
        return error;
    }

    @Override
    public ObservableList<ScheduleTableDataViewModel> getSchedules() {
        return schedule;
    }
}
