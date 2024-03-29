package client.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Region;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public abstract class ViewCreator {
    private Map<View, ViewController> viewControllers;
    
    public ViewCreator() {
        viewControllers = new HashMap<>();
    }
    
    public ViewController getViewController(View view) {
        ViewController controller = viewControllers.get(view);
        if (controller == null) {
            controller = loadFromFXML(view.getFxmlFile());
            viewControllers.put(view, controller);
        }
        else {
            viewControllers.get(view).reset();
        }
        return controller;
    }
    
    protected abstract void initViewController(ViewController controller, Region root);
    
    private ViewController loadFromFXML(String fxmlFile) {
        ViewController viewController = null;
        try {
            FXMLLoader loader = new FXMLLoader();
            URL url = getClass().getResource(fxmlFile);
            loader.setLocation(url);
            Region root = loader.load();
            viewController = loader.getController();
            initViewController(viewController, root);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return viewController;
    }
}
