package server.model.domain.appointment;

public enum Result
{
    NEGATIVE("Negative"),
    POSITIVE("Positive"),
    INCONCLUSIVE("Inconclusive"),
    NORESULTSAVAILABLE("No results available");

    private String result;

    Result(String result) {
            this.result = result;
        }

    @Override
    public String toString() {
        return result;
    }

    public static Result fromString(String value) {
        for (Result option : Result.values()) {
            if (option.result.equalsIgnoreCase(value)) {
                return option;
            }
        }
        return null;
    }

}
