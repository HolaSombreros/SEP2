package server.model;

import java.util.ArrayList;

public interface LoginModel
{
    void login(User user);
    void sendServerMessage(ServerMessage message);
    UsersList getOnlineUsers();
}
