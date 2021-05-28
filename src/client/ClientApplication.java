package client;

import client.model.Model;
import client.model.ModelManager;
import client.view.View;
import client.view.ViewHandler;
import client.viewmodel.ViewModelFactory;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import server.model.domain.user.Administrator;
import server.model.domain.user.Nurse;
import server.model.domain.user.Staff;
import server.model.domain.user.User;
import utility.observer.event.ObserverEvent;
import utility.observer.listener.LocalListener;

public class ClientApplication extends Application implements LocalListener<Object, Object>
{
    private Model model;
    private ViewModelFactory viewModelFactory;
    private ViewHandler viewHandler;

    @Override public void start(Stage stage) throws Exception
    {
        model = new ModelManager();
        model.addListener(this, "Disconnect");
        viewModelFactory = new ViewModelFactory(model);
        viewHandler = new ViewHandler(viewModelFactory);
        viewHandler.start(stage);
    }

    @Override public void stop()
    {
        User user = viewModelFactory.getViewState().getUser();
        // if the user is logged in, log him out
        if (user != null)
        {
            model.logout(user);
            User selected = viewModelFactory.getViewState().getSelectedUser();
            if (user instanceof Administrator && selected != null)
            {
                model.lockChat(selected.getCpr(), false);
            }
        }
        model.close();
    }

    @Override public void propertyChange(ObserverEvent<Object, Object> event) {
        User user = viewModelFactory.getViewState().getUser();
        if (user instanceof Staff) {
            if (user.equals(event.getValue2()))
                Platform.runLater(() -> {
                    model.logout(user);
                    viewHandler.openView(View.LOGIN);
                });
        }
    }
}