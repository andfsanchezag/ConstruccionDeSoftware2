package app.domain.model;


public class Pet {
    private final String petId;
    private final String ownerDocumentId;
    private final String name;
    private final int age;
    private final Species species;
    private final String breed;
    private final String color;
    private final String size;
    private final double weight;

    public Pet(String petId, String ownerDocumentId, String name, int age, Species species,
               String breed, String color, String size, double weight) {
        this.petId = petId;
        this.ownerDocumentId = ownerDocumentId;
        this.name = name;
        this.age = age;
        this.species = species;
        this.breed = breed;
        this.color = color;
        this.size = size;
        this.weight = weight;
    }

    public String getPetId() {
        return petId;
    }

    public String getOwnerDocumentId() {
        return ownerDocumentId;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public Species getSpecies() {
        return species;
    }

    public String getBreed() {
        return breed;
    }

    public String getColor() {
        return color;
    }

    public String getSize() {
        return size;
    }

    public double getWeight() {
        return weight;
    }
}

