package client.mediator;

public interface LocalClientModel extends LocalClientAppointmentModel, LocalClientUserModel {
    void close();
}
