package server.model;

import java.util.ArrayList;

public interface LoginModel
{
    void login(Patient user);
    void sendServerMessage(ServerMessage message);
    UsersList getOnlineUsers();
}
