package server.model.domain.chat;

import server.model.domain.user.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Chat implements Serializable {

    private List<Message> messages;

    public Chat() {
        messages = new ArrayList<>();
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
        if (index < 0 || index > size()) {
            throw new IllegalArgumentException("Please specify a valid index");
        }
        return messages.get(index);
    }
    public boolean contains(Message message){
        for (Message m : messages) {
            if (m.equals(message)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof Chat))
            return false;

        Chat other = (Chat) obj;
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
