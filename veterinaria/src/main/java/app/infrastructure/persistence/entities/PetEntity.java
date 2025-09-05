package app.infrastructure.persistence.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "pets")
public class PetEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private long id;

    @ManyToOne(fetch = FetchType.EAGER) // Muchas mascotas pueden pertenecer a un usuario
    @JoinColumn(name = "owner_id", nullable = false)
    private UserEntity owner;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false)
    private int age;

    @Column(nullable = false)
    private double weight;  // corregido de "weigth"

    @Column(nullable = false, length = 50)
    private String species; // guardamos como String (mapeo manual del enum)

    @Column(columnDefinition = "TEXT")
    private String features;

    @Column(length = 100)
    private String breed;

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserEntity getOwner() {
        return owner;
    }

    public void setOwner(UserEntity owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public String getBreed() {
        return breed;
    }

	public void setId(long id) {
		this.id = id;
	}

	public void setBreed(String breed) {
		this.breed = breed;
	}
    
    
    
    
}
