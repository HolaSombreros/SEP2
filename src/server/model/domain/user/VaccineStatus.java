package server.model.domain.user;

import java.io.Serializable;

public abstract class VaccineStatus implements Serializable {
    // Methods are overwritten where necessary
    public void apply(Patient patient) {

    }

    public void approve(Patient patient) {

    }

    public void decline(Patient patient) {

    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof VaccineStatus;
    }
}
