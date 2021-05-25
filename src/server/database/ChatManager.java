package server.database;

import server.model.domain.chat.Message;
import server.model.domain.chat.MessageStatus;
import server.model.domain.user.Administrator;
import server.model.domain.user.Patient;
import server.model.domain.user.User;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class ChatManager {
    public ChatManager() {
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



}
