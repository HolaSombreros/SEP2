package client.model;

public interface Model extends AppointmentModel, UserModel, FAQModel, MessageModel {

    void close();
}
