package server.model;

public interface ServerModel extends ServerAppointmentModel, ServerUserModel, ServerFAQModel, ServerMessageModel
{
    void close();
}
