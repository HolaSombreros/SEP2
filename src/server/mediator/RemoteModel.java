package server.mediator;

import server.model.domain.appointment.*;
import server.model.domain.user.Patient;
import server.model.domain.user.User;
import server.model.domain.user.UserList;
import utility.observer.subject.RemoteSubject;
import java.rmi.RemoteException;
import java.time.LocalDate;

public interface RemoteModel extends RemoteAppointmentModel, RemoteUserModel, RemoteSubject<User, Appointment>
{

}
