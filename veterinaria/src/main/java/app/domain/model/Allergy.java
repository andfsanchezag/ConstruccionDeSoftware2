package app.domain.model;


public class Allergy {
    private final String medicationName;
    private final String description;

    public Allergy(String medicationName, String description) {
        this.medicationName = medicationName;
        this.description = description;
    }

    public String getMedicationName() {
        return medicationName;
    }

    public String getDescription() {
        return description;
    }
}
