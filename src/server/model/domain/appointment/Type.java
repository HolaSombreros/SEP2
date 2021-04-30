package server.model.domain.appointment;

public enum Type
{
    TEST("Test"),
    VACCINE("Vaccine");

    private String type;

    Type(String type) {
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
