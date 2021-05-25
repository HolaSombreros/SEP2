package client.viewmodel;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import server.model.domain.faq.FAQ;

public interface FAQViewModelInterface {
    void reset();
    void addEditFAQ();
    void remove();
    void setSelectedBox(TitledPane box);
    ObservableList<VBox> getFAQContent();
    BooleanProperty isAdminProperty();
    StringProperty errorLabelProperty();
    BooleanProperty getUpdatedProperty();
}
