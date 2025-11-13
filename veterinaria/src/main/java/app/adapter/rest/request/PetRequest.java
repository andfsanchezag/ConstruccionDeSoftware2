package app.adapter.rest.request;

public class PetRequest {
    private String ownerDocument;
    private String name;
    private String age;
    private String weigth;
    private String spices;
    private String features;
    private String breed;

    public String getOwnerDocument() { return ownerDocument; }
    public void setOwnerDocument(String ownerDocument) { this.ownerDocument = ownerDocument; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getAge() { return age; }
    public void setAge(String age) { this.age = age; }
    public String getWeigth() { return weigth; }
    public void setWeigth(String weigth) { this.weigth = weigth; }
    public String getSpices() { return spices; }
    public void setSpices(String spices) { this.spices = spices; }
    public String getFeatures() { return features; }
    public void setFeatures(String features) { this.features = features; }
    public String getBreed() { return breed; }
    public void setBreed(String breed) { this.breed = breed; }
}
