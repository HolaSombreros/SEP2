package server.database;

import server.model.domain.user.Notification;
import server.model.domain.user.Patient;

import java.sql.*;

public class NotificationManager {

    public Notification addNotification(String text, boolean status, Patient patient) throws SQLException {
        try (Connection connection = DatabaseManager.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO notification (text, status, patient_cpr) VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, text);
            statement.setBoolean(2, status);
            statement.setString(3, patient.getCpr());
            statement.executeUpdate();
            ResultSet keys = statement.getGeneratedKeys();
            if (keys.next()) {
                return new Notification(keys.getInt("id"), text, status, patient);
            } else {
                throw new IllegalStateException("No notification found");
            }
        }
    }


}
