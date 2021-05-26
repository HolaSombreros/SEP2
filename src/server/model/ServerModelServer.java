package server.model;

public interface ServerModelServer extends ServerAppointmentModel, ServerUserModel, ServerFAQModel, ServerMessageModel
{

    void close();
}
