package client.viewmodel;

import javafx.beans.property.BooleanProperty;
import javafx.collections.ObservableList;
import javafx.scene.Node;

public interface FAQViewModelInterface {
    void reset();
    ObservableList<Node> getFAQContent();
    BooleanProperty isAdminProperty();
}
