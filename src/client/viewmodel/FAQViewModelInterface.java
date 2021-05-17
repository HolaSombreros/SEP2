package client.viewmodel;

import javafx.collections.ObservableList;
import javafx.scene.Node;

public interface FAQViewModelInterface {
    void reset();
    ObservableList<Node> getFAQContent();
}
