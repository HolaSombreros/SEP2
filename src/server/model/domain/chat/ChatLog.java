package server.model.domain.chat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ChatLog implements Serializable {
    private List<Message> messages;
    private boolean locked;

    public ChatLog() {
        messages = new ArrayList<>();
        locked = false;
    }

    public void add(Message message){
        if(message != null)
            messages.add(message);
    }

    public List<Message> getMessages(){
        return messages;
    }

    public int size(){
        return messages.size();
    }

    public Message get(int index) {
        if (index < 0 || index >= size()) {
            throw new IllegalArgumentException("Please specify a valid index");
        }
        return messages.get(index);
    }
    
    public List<Message> getUnreadMessages() {
        List<Message> list = new ArrayList<>();
        for (Message message : messages) {
            if (message.getStatus() instanceof UnreadStatus) {
                list.add(message);
            }
        }
        return list;
    }
    
    public boolean isChatLocked() {
        return locked;
    }
    
    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof ChatLog))
            return false;

        ChatLog other = (ChatLog) obj;
        if (other.size() != size())
            return false;

        for (int i = 0; i < size(); i++) {
            if (!messages.get(i).equals(other.messages.get(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        String str = "";
        for (Message message : messages) {
            str += "\n" + message.toString();
        }
        return str;
    }
}
