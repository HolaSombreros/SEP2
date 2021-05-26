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
    
    public Message addMessage(String message, LocalDate date, LocalTime time, MessageStatus status, Patient patient, Administrator admin) throws SQLException{
        try (Connection connection = DatabaseManager.getInstance().getConnection()){
            PreparedStatement statement = connection.prepareStatement("INSERT INTO message (text,date,time,status,patient_cpr,administrator_cpr) VALUES (?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1,message);
            statement.setDate(2, Date.valueOf(date));
            statement.setTime(3,Time.valueOf(time));
            statement.setString(4,status.toString());
            statement.setString(5, patient.getCpr());
            if(admin != null)
                statement.setString(6, admin.getCpr());
            else
                statement.setString(6,null);
            statement.executeUpdate();
            ResultSet keys = statement.getGeneratedKeys();
            if(keys.next()){
                return new Message(keys.getInt("message_id"), message, date, time, status, patient, admin);
            }
            else{
                throw new SQLException("No keys were generated");
            }
        }
    }

    public Chat getMessageByPatient(Patient patient) throws SQLException{
        try(Connection connection = DatabaseManager.getInstance().getConnection()){
            Chat list = new Chat();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM message WHERE patient_cpr = ?");
            statement.setString(1, patient.getCpr());
            ResultSet result = statement.executeQuery();
            while(result.next()){
                int id = result.getInt("message_id");
                String message = result.getString("text");
                LocalTime time = result.getTime("time").toLocalTime();
                LocalDate date = result.getDate("date").toLocalDate();
                String admin_cpr = result.getString("administrator_cpr");
                Administrator admin = null;
                if(admin_cpr != null)
                    admin = userManager.getAdministrator(admin_cpr);
                MessageStatus status = null;
                switch (result.getString("status")){
                    case "Read":
                        status = new ReadStatus();
                        break;
                    case "Unread":
                        status = new UnreadStatus();
                        break;
                }
                list.add(new Message(id, message, date, time, status, patient, admin));
            }
            return list;
        }
    }


}
