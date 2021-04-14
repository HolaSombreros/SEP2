package server.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ServerMessage implements Serializable
{

    private String cpr;
    private String password;
    private String text;
    private LocalDateTime time;

    public ServerMessage(String cpr, String password,String text){

        this.cpr = cpr;
        this.password = password;
        this.text = text;
        this.time = LocalDateTime.now();
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
