/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.adapter.in.validators;

import org.springframework.stereotype.Component;

import app.domain.model.emuns.Spices;

/**
 *
 * @author ESTUDIANTE
 */

@Component
public class PetValidator extends SimpleValidator {

    public String nameValidator(String value) throws Exception {
        return stringValidator("Nombre de la mascota ", value);
    }

    public String featuresValidator(String value) throws Exception {
        return stringValidator("Nombre de la mascota ", value);
    }
    
    public Spices spicesValidator(String value) throws Exception {
        stringValidator("Nombre de la mascota ", value);
        return Spices.valueOf(value.toUpperCase());
    }
    
    public String breedValidator(String value) throws Exception {
        return stringValidator("Nombre de la mascota ", value);
    }
    public double weigthValidator(String value) throws Exception{
        return doubleValidator("el peso de la mascota", value);
    }
    public int ageValidator(String value) throws Exception{
        return integerValidator("el peso de la mascota", value);
    }
    
    public long idValidator(String value) throws Exception{
        return longValidator("el peso de la mascota", value);
    }
    

}
