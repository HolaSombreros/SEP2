package server.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ServerMessage
{
    private String type;
    private String cpr;
    private String password;
    private String text;
    private LocalDateTime time;

    public ServerMessage(String type, String cpr, String password,String text){
        this.type = type;
        this.cpr = cpr;
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

    public String getCpr() {
        return cpr;
    }
    public String getPassword(){
        return password;
    }
    public String getDateTimeAsString() {
        return time.format(DateTimeFormatter.ofPattern("dd/MM/yyy HH:mm:ss"));
    }

    public String toString() {
        return "[" + getDateTimeAsString() + "] " + cpr + ": " + text;
    }

}
