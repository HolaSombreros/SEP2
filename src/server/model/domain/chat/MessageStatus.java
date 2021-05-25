package server.model.domain.chat;

import server.model.domain.appointment.Status;

import java.io.Serializable;

public abstract class MessageStatus implements Serializable {

    public void read(Message message){

    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof MessageStatus;
    }



}
