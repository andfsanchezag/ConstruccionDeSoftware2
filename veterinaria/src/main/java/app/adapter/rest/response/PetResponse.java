package app.adapter.rest.response;

public class PetResponse {
    private long id;
    private String name;
    private int age;
    private double weigth;
    private String spices;
    private String features;
    private String breed;
    private long ownerDocument;

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    public double getWeigth() { return weigth; }
    public void setWeigth(double weigth) { this.weigth = weigth; }
    public String getSpices() { return spices; }
    public void setSpices(String spices) { this.spices = spices; }
    public String getFeatures() { return features; }
    public void setFeatures(String features) { this.features = features; }
    public String getBreed() { return breed; }
    public void setBreed(String breed) { this.breed = breed; }
    public long getOwnerDocument() { return ownerDocument; }
    public void setOwnerDocument(long ownerDocument) { this.ownerDocument = ownerDocument; }
}
