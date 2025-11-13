package app.adapter.in.builder;

import app.adapter.in.validators.PetValidator;
import app.adapter.in.validators.UserValidator;
import app.application.exceptions.InputsException;
import app.domain.model.Pet;
import app.domain.model.emuns.Spices;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@org.mockito.junit.jupiter.MockitoSettings(strictness = org.mockito.quality.Strictness.LENIENT)
class PetBuilderTest {

    @Mock
    private PetValidator petValidator;

    @Mock
    private UserValidator userValidator;

    @InjectMocks
    private PetBuilder petBuilder;

    @BeforeEach
    void setUp() {
        try {
            doAnswer(i -> {
                String s = i.getArgument(0);
                if (s == null || !s.matches("^-?\\d+$")) throw new InputsException("documento invalido");
                return Long.parseLong(s);
            }).when(userValidator).documentValidator(anyString());
            when(petValidator.nameValidator(anyString())).thenAnswer(i -> i.getArgument(0));
            doAnswer(i -> {
                String s = i.getArgument(0);
                if (s == null || !s.matches("^-?\\d+$")) throw new InputsException("edad invalida");
                return Integer.parseInt(s);
            }).when(petValidator).ageValidator(anyString());
            doAnswer(i -> {
                String s = i.getArgument(0);
                if (s == null || !s.matches("^-?\\d*(?:\\.\\d+)?$")) throw new InputsException("peso invalido");
                return Double.parseDouble(s);
            }).when(petValidator).weigthValidator(anyString());
            when(petValidator.spicesValidator(anyString())).thenAnswer(i -> Spices.valueOf(i.getArgument(0)));
            when(petValidator.featuresValidator(anyString())).thenAnswer(i -> i.getArgument(0));
            when(petValidator.breedValidator(anyString())).thenAnswer(i -> i.getArgument(0));
        } catch (Exception e) {
            fail("Setup failed");
        }
    }

    @Test
    void builder_withValidData_shouldReturnPet() throws Exception {
        // Arrange
        String document = "123456";
        String name = "Rex";
        String age = "5";
        String weigth = "25.5";
        String spices = "DOG";
        String features = "Friendly";
        String breed = "Labrador";

        // Act
        Pet result = petBuilder.builder(document, name, age, weigth, spices, features, breed);

        // Assert
        assertNotNull(result);
        assertEquals("Rex", result.getName());
        assertEquals(5, result.getAge());
        assertEquals(25.5, result.getWeigth(), 0.001);
        assertEquals(Spices.DOG, result.getSpices());
        assertEquals("Friendly", result.getFeatures());
        assertEquals("Labrador", result.getBreed());
        assertNotNull(result.getOwner());
        assertEquals(123456L, result.getOwner().getDocument());

        verify(userValidator).documentValidator(document);
        verify(petValidator).nameValidator(name);
        verify(petValidator).ageValidator(age);
        verify(petValidator).weigthValidator(weigth);
        verify(petValidator).spicesValidator(spices);
        verify(petValidator).featuresValidator(features);
        verify(petValidator).breedValidator(breed);
    }

    @Test
    void builder_withInvalidDocument_shouldThrowInputsException() throws Exception {
        // Arrange
        doThrow(new InputsException("documento invalido")).when(userValidator).documentValidator("invalid");

        // Act & Assert
        assertThrows(InputsException.class, () -> 
            petBuilder.builder("invalid", "Rex", "5", "25.5", "DOG", "Friendly", "Labrador")
        );
    }

    @Test
    void builder_withInvalidAge_shouldThrowInputsException() throws Exception {
        // Arrange
        doThrow(new InputsException("edad invalida")).when(petValidator).ageValidator("abc");

        // Act & Assert
        assertThrows(InputsException.class, () -> 
            petBuilder.builder("123", "Rex", "abc", "25.5", "DOG", "Friendly", "Labrador")
        );
    }

    @Test
    void builder_withInvalidWeight_shouldThrowInputsException() throws Exception {
        // Arrange
        doThrow(new InputsException("peso invalido")).when(petValidator).weigthValidator("invalid");

        // Act & Assert
        assertThrows(InputsException.class, () -> 
            petBuilder.builder("123", "Rex", "5", "invalid", "DOG", "Friendly", "Labrador")
        );
    }

    @Test
    void builder_withInvalidSpices_shouldThrowException() throws Exception {
        // Arrange
        doThrow(new IllegalArgumentException("Invalid spices")).when(petValidator).spicesValidator("INVALID");

        // Act & Assert
        assertThrows(Exception.class, () -> 
            petBuilder.builder("123", "Rex", "5", "25.5", "INVALID", "Friendly", "Labrador")
        );
    }

    @Test
    void builder_withNullName_shouldThrowInputsException() throws Exception {
        // Arrange
        when(petValidator.nameValidator(null)).thenThrow(new InputsException("nombre nulo"));

        // Act & Assert
        assertThrows(InputsException.class, () -> 
            petBuilder.builder("123", null, "5", "25.5", "DOG", "Friendly", "Labrador")
        );
    }

    @Test
    void builder_withAllSpicesTypes_shouldWork() throws Exception {
        // Test all valid spices
        for (Spices spice : Spices.values()) {
            when(petValidator.spicesValidator(spice.name())).thenReturn(spice);
            
            Pet result = petBuilder.builder("123", "Pet", "3", "10.0", spice.name(), "features", "breed");
            
            assertEquals(spice, result.getSpices());
        }
    }
}
