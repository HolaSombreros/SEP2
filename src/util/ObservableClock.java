package util;

import utility.observer.listener.GeneralListener;
import utility.observer.subject.LocalSubject;
import utility.observer.subject.PropertyChangeAction;
import utility.observer.subject.PropertyChangeProxy;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ObservableClock implements Runnable, LocalSubject<String, String> {
    private PropertyChangeAction<String, String> property;
    private DateTimeFormatter formatter;
    
    public ObservableClock() {
        property = new PropertyChangeProxy<>(this);
        formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    }
    
    @Override
    public void run() {
        while (true) {
            try {
                LocalTime time = LocalTime.now();
                String value = time.format(formatter);
                property.firePropertyChange("ObservableClock", null, value);
                Thread.sleep(1000);
            }
            catch (InterruptedException e) {
                // nothing
            }
        }
    }
    
    @Override
    public boolean addListener(GeneralListener<String, String> listener, String... propertyNames) {
        return property.addListener(listener, propertyNames);
    }
    
    @Override
    public boolean removeListener(GeneralListener<String, String> listener, String... propertyNames) {
        return property.removeListener(listener, propertyNames);
    }
}
