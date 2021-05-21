package server.model.domain.user;

import java.time.LocalDate;

public class Nurse extends Staff {
    private ScheduleList scheduleList;

    public Nurse(String cpr, String password, String firstName, String middleName, String lastName, Address address, String phone, String email, String employeeId) {
        super(cpr, password, firstName, middleName, lastName, address, phone, email, employeeId);
        scheduleList = new ScheduleList();
    }

    public Nurse(String cpr, String password, String firstName, String lastName, Address address, String phone, String email, String employeeId) {
        this(cpr, password, firstName, null, lastName, address, phone, email, employeeId);
    }
    
    public ScheduleList getScheduleList() {
        return scheduleList;
    }

    public void setScheduleList(ScheduleList scheduleList) {
        this.scheduleList = scheduleList;
    }

    public void addSchedule(Schedule schedule) {
        scheduleList.add(schedule);
    }

    public void editSchedule(Schedule schedule) {
        for (Schedule schedule1 : scheduleList.getSchedules())
            if (schedule1.equals(schedule))
                schedule1.setShift(schedule.getShift());
    }

    public void removeSchedule(Schedule schedule)
    {
        boolean remove = false;
        for (Schedule schedule1 : scheduleList.getSchedules())
            if (schedule1.equals(schedule))
            {
                schedule = schedule1;
                remove = true;
            }
        if (remove)
        scheduleList.remove(schedule);
    }

    public Schedule getSchedule(LocalDate date)
    {
        for (Schedule schedule : scheduleList.getSchedules())
            if (schedule.getDateFrom().equals(date))
                return schedule;
        return null;
    }

    public boolean worksThatWeek(LocalDate date)
    {
        for (Schedule s : scheduleList.getSchedules())
            if (s.getDateFrom().equals(date))
                return true;
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Nurse)) {
            return false;
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return super.toString() + getEmployeeId() + scheduleList.toString();
    }
}
