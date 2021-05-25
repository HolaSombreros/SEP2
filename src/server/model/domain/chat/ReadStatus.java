package server.model.domain.chat;

public class ReadStatus extends MessageStatus{



    @Override
    public boolean equals(Object obj) {
        return obj instanceof ReadStatus;
    }

    @Override
    public String toString() {
        return "Read";
    }
}
