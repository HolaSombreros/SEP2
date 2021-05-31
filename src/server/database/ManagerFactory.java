package server.database;

public class ManagerFactory {
    private AppointmentManager appointmentManager;
    private AddressManager addressManager;
    private UserManager userManager;
    private NurseScheduleManager nurseScheduleManager;
    private FAQManager faqManager;
    private ChatManager chatManager;
    private NotificationManager notificationManager;

    public ManagerFactory() {
        addressManager = new AddressManager();
        userManager = new UserManager(addressManager);
        appointmentManager = new AppointmentManager(userManager);
        nurseScheduleManager = new NurseScheduleManager();
        faqManager = new FAQManager();
        chatManager = new ChatManager(userManager);
        notificationManager = new NotificationManager(userManager);
    }


    public AppointmentManager getAppointmentManager() {
        return appointmentManager;
    }

    public AddressManager getAddressManager() {
        return addressManager;
    }

    public UserManager getUserManager() {
        return userManager;
    }

    public NurseScheduleManager getNurseScheduleManager() {
        return nurseScheduleManager;
    }

    public FAQManager getFAQManager() {
        return faqManager;
    }

    public ChatManager getChatManager() {
        return chatManager;
    }

    public NotificationManager getNotificationManager() {
        return notificationManager;
    }

}
