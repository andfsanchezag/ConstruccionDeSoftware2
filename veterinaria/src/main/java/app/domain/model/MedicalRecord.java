package veterinaria.domain.model;

import java.util.Map;
import java.util.HashMap;

public class MedicalRecord {
    private final String petId;
    private final Map<String, MedicalVisit> visitsByDate;

    public MedicalRecord(String petId) {
        this.petId = petId;
        this.visitsByDate = new HashMap<>();
    }

    public void addVisit(String date, MedicalVisit visit) {
        visitsByDate.put(date, visit);
    }

    public MedicalVisit getVisitByDate(String date) {
        return visitsByDate.get(date);
    }

    public Map<String, MedicalVisit> getAllVisits() {
        return visitsByDate;
    }

    public String getPetId() {
        return petId;
    }
}
