package client.model;

public interface Model extends AppointmentModel, UserModel, FAQModel {

    void close();
}
