package client.model;

public interface Model extends AppointmentModel, UserModel {
    void close();
}
