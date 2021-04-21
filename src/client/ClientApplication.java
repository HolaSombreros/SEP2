package client;

import client.model.Model;
import client.model.ModelManager;
import client.view.ViewHandler;
import client.viewmodel.ViewModelFactory;
import client.viewmodel.ViewState;
import javafx.application.Application;
import javafx.stage.Stage;

public class ClientApplication extends Application {
    private Model model;
    
    @Override
    public void start(Stage stage) {
        model = new ModelManager();
        ViewState viewState = new ViewState();
        ViewModelFactory viewModelFactory = new ViewModelFactory(model,viewState);
        ViewHandler viewHandler = new ViewHandler(viewModelFactory);
        viewHandler.start(stage);
    }
    
    @Override
    public void stop() {
        // TODO: maybe implement something here?
    }
}
