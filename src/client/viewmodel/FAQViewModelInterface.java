package client.viewmodel;

import javafx.beans.property.BooleanProperty;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

public interface FAQViewModelInterface {
    void reset();
    ObservableList<VBox> getFAQContent();
    BooleanProperty isAdminProperty();
}
