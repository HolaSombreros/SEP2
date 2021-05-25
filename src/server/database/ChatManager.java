package server.database;

import server.model.domain.chat.*;
import server.model.domain.user.Administrator;
import server.model.domain.user.Patient;
import server.model.domain.user.User;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class ChatManager {

    private UserManager userManager;
    public ChatManager(UserManager userManager) {
        this.userManager = userManager;
    }


    public Message addMessage(String message, LocalDate date, LocalTime time, MessageStatus status, Patient patient, Administrator admin, User sender) throws SQLException{
        try (Connection connection = DatabaseManager.getInstance().getConnection()){
            PreparedStatement statement = connection.prepareStatement("INSERT INTO message (text,date,time,status,patient_cpr,administrator_cpr,sent_by) VALUES (?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1,message);
            statement.setDate(2, Date.valueOf(date));
            statement.setTime(3,Time.valueOf(time));
            statement.setString(4,status.toString());
            statement.setString(5, patient.getCpr());
            statement.setString(6, admin.getCpr());
            statement.setString(7,sender.getCpr());
            statement.executeUpdate();
            ResultSet keys = statement.getGeneratedKeys();
            if(keys.next()){
                return new Message(keys.getInt("message_id"),message,date,time,status,patient,admin,sender);
            }
            else{
                throw new SQLException("No keys were generated");
            }
        }
    }

    public Chat getMessagesBySender(User sender) throws SQLException{
        try(Connection connection = DatabaseManager.getInstance().getConnection()){
            Chat list = new Chat();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM message WHERE sent_by = ?");
            statement.setString(1, sender.getCpr());
            ResultSet result = statement.executeQuery();
            while(result.next()){
                int id = result.getInt("message_id");
                String message = result.getString("message");
                LocalTime time = result.getTime("time").toLocalTime();
                LocalDate date = result.getDate("date").toLocalDate();
                Patient patient = userManager.getPatient(result.getString("patient_cpr"));
                Administrator admin = userManager.getAdministrator(result.getString("administrator_cpr"));
                MessageStatus status = null;
                switch (result.getString("status")){
                    case "Read":
                        status = new ReadStatus();
                        break;
                    case "Unread":
                        status = new UnreadStatus();
                        break;
                }
                list.add(new Message(id, message, date, time, status, patient, admin, sender));
            }
            return list;
        }
    }


}
