/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.adapter.in.builder;

import app.adapter.in.validators.PetValidator;
import app.adapter.in.validators.UserValidator;
import app.domain.model.Pet;
import app.domain.model.User;

/**
 *
 * @author ESTUDIANTE
 */
public class PetBuilder {
    
    private PetValidator petValidator;
    private UserValidator userValidator;
    
    public Pet builder(String document, String name, String age, String weigth, String spices, String features, String breed) throws Exception {
        Pet pet = new Pet();
        User owner = new User();
        owner.setDocument(userValidator.documentValidator(document));
        pet.setOwner(owner);
        pet.setAge(petValidator.ageValidator(age));
        pet.setBreed(petValidator.breedValidator(breed));
        pet.setFeatures(petValidator.featuresValidator(features));
        pet.setWeigth(petValidator.weigthValidator(weigth));
        pet.setSpices(petValidator.spicesValidator(spices));
        return pet;
    }
    
}
