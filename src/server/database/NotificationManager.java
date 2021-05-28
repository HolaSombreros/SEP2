package server.database;

import server.model.domain.user.Notification;
import server.model.domain.user.NotificationList;
import server.model.domain.user.Patient;

import java.sql.*;

public class NotificationManager {

    private UserManager userManager;

    public NotificationManager(UserManager userManager) {
        this.userManager = userManager;
    }

    public Notification addNotification(String text, boolean seen, Patient patient) throws SQLException {
        try (Connection connection = DatabaseManager.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO notification (text, seen, patient_cpr) VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, text);
            statement.setBoolean(2, seen);
            statement.setString(3, patient.getCpr());
            statement.executeUpdate();
            ResultSet keys = statement.getGeneratedKeys();
            if (keys.next()) {
                return new Notification(keys.getInt("id"), text, seen, patient);
            } else {
                throw new IllegalStateException("No notification found");
            }
        }
    }

    public NotificationList getAllUnSeenNotifications() throws SQLException{
        try (Connection connection = DatabaseManager.getInstance().getConnection()){
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM notification WHERE seen = false");
            ResultSet resultSet = statement.executeQuery();
            NotificationList list = new NotificationList();
            while(resultSet.next()){
                int id = resultSet.getInt("id");
                String text = resultSet.getString("text");
                boolean seen = resultSet.getBoolean("seen");
                Patient patient = userManager.getPatient(resultSet.getString("patient_cpr"));
                list.add(new Notification(id, text, seen, patient));
            }
            return list;
        }
    }

    public void updateSeenStatus(int id) throws SQLException{
        try (Connection connection = DatabaseManager.getInstance().getConnection()){
            PreparedStatement statement = connection.prepareStatement("UPDATE notification SET seen = ? WHERE id = ?");
            statement.setBoolean(1,true);
            statement.setInt(2,id);
            statement.executeUpdate();
        }
    }
}
