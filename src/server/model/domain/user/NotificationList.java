package server.model.domain.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class NotificationList implements Serializable {
    private List<Notification> notifications;

    public NotificationList() {
        this.notifications = new ArrayList<>();
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void add(Notification notification){
        if(notification != null)
            notifications.add(notification);
    }

    public void remove(Notification notification){
        if(notification != null)
            notifications.remove(notification);
    }

    public NotificationList getNotificationsByPatient(Patient patient){
        NotificationList list = new NotificationList();
        for(Notification notification : notifications)
            if(notification.getPatient().equals(patient) && !notification.isSeen())
                list.add(notification);
        return list;
    }

    public Notification getById(int id){
        for(Notification notification : notifications)
            if(notification.getId() == id)
                return notification;
        return null;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof NotificationList))
            return false;
        NotificationList other = (NotificationList) obj;
        if (other.notifications.size() != notifications.size())
            return false;
        for (int i = 0; i < notifications.size(); i++) {
            if (!notifications.get(i).equals(other.notifications.get(i)))
                return false;
        }
        return true;
    }

    @Override
    public String toString() {
        String all = "";
        for (Notification notification : notifications) {
            all += notification.toString() + " \n";
        }
        return all;
    }
}
