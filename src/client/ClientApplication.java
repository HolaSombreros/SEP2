package client;

import client.model.Model;
import client.model.ModelManager;
import client.view.ViewHandler;
import client.viewmodel.ViewModelFactory;
import javafx.application.Application;
import javafx.stage.Stage;
import server.model.domain.user.Administrator;
import server.model.domain.user.User;

public class ClientApplication extends Application {
    private Model model;
    private ViewModelFactory viewModelFactory;
    
    @Override
    public void start(Stage stage) throws Exception {
        model = new ModelManager();
        viewModelFactory = new ViewModelFactory(model);
        ViewHandler viewHandler = new ViewHandler(viewModelFactory);
        viewHandler.start(stage);
    }
    
    @Override
    public void stop() {
        User user = viewModelFactory.getViewState().getUser();
        // if the user is logged in, log him out
        if (user != null) {
            model.logout(user);
            User selected = viewModelFactory.getViewState().getSelectedUser();
            if (user instanceof Administrator && selected != null) {
                model.lockChat(selected.getCpr(), false);
            }
        }
        model.close();
    }
}
