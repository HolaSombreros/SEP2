package server.model.domain.chat;

import server.model.domain.user.User;

import java.util.ArrayList;
import java.util.List;

public class Chat {

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

    public void addAll(List<Message> messageList){
        messages.addAll(messageList);
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

    public Chat getMessagesBySender(User user){
        if(user == null)
            throw new IllegalArgumentException("User cannot be null");
        Chat list = new Chat();
        for(Message message : messages)
            if(message.getSender().getCpr().equals(user.getCpr()))
                list.add(message);
        return list;
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
