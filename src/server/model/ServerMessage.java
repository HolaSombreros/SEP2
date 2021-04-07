package server.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ServerMessage
{
    private String type;
    private String username;
    private String password;
    private String text;
    private LocalDateTime time;

    public ServerMessage(String type, String username, String password,String text){
        this.type = type;
        this.username = username;
        this.password = password;
        this.text = text;
        this.time = LocalDateTime.now();
    }
    public String getType() {
        return type;
    }

    public String getText() {
        return text;
    }

    public String getUsername() {
        return username;
    }
    public String getPassword(){
        return password;
    }
    public String getDateTimeAsString() {
        return time.format(DateTimeFormatter.ofPattern("dd/MM/yyy HH:mm:ss"));
    }

    public String toString() {
        return "[" + getDateTimeAsString() + "] " + username + ": " + text;
    }

}
