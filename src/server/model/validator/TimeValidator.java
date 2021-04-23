package server.model.validator;

public class TimeValidator {

    /**
     * Throws exception if the hour is a negative integer or it is 24 or more
     *
     * @param hour
     */
    public static void setHour(int hour) {
        if (hour >= 24 || hour < 0)
            throw new IllegalArgumentException("Invalid time");
    }
    /**
     * Throws exception if the minute is a negative integer or it is 60 or more
     *
     * @param minute
     */
    public static void setMinute(int minute) {
        if (minute >= 60 || minute < 0)
            throw new IllegalArgumentException("Invalid time");
    }
}
