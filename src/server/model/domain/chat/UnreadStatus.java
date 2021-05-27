package server.model.domain.chat;

public class UnreadStatus extends MessageStatus {
    @Override
    public void read(Message message) {
        message.setStatus(new ReadStatus());
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof UnreadStatus;
    }

    @Override
    public String toString() {
        return "Unread";
    }
}
