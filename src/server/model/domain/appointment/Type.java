package server.model.domain.appointment;

import java.io.Serializable;

public enum Type implements Serializable {
    TEST("Test"),
    VACCINE("Vaccine");

    private String type;

    private Type(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }

    public static Type fromString(String value) {
        for (Type option : Type.values()) {
            if (option.type.equalsIgnoreCase(value)) {
                return option;
            }
        }
        return null;
    }
}
