package app.adapter.in.builder;

import app.adapter.in.validators.UserValidator;
import app.application.exceptions.InputsException;
import app.domain.model.User;
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
class UserBuilderTest {

    @Mock
    private UserValidator userValidator;

    @InjectMocks
    private UserBuilder userBuilder;

    @BeforeEach
    void setUp() {
        // Setup common mock behaviors
        try {
            when(userValidator.nameValidator(anyString())).thenAnswer(i -> i.getArgument(0));
            doAnswer(i -> {
                String s = i.getArgument(0);
                if (s == null || !s.matches("^-?\\d+$")) throw new InputsException("documento invalido");
                return Long.parseLong(s);
            }).when(userValidator).documentValidator(anyString());
            doAnswer(i -> {
                String s = i.getArgument(0);
                if (s == null || !s.matches("^-?\\d+$")) throw new InputsException("edad invalida");
                return Integer.parseInt(s);
            }).when(userValidator).ageValidator(anyString());
            when(userValidator.userNameValidator(anyString())).thenAnswer(i -> i.getArgument(0));
            when(userValidator.passwordValidator(anyString())).thenAnswer(i -> i.getArgument(0));
        } catch (Exception e) {
            fail("Setup failed");
        }
    }

    @Test
    void build_withValidData_shouldReturnUser() throws Exception {
        // Arrange
        String name = "Juan Perez";
        String document = "123456789";
        String age = "30";
        String userName = "jperez";
        String password = "pass123";

        // Act
        User result = userBuilder.build(name, document, age, userName, password);

        // Assert
        assertNotNull(result);
        assertEquals("Juan Perez", result.getName());
        assertEquals(123456789L, result.getDocument());
        assertEquals(30, result.getAge());
        assertEquals("jperez", result.getUserName());
        assertEquals("pass123", result.getPassword());

        verify(userValidator).nameValidator(name);
        verify(userValidator).documentValidator(document);
        verify(userValidator).ageValidator(age);
        verify(userValidator).userNameValidator(userName);
        verify(userValidator).passwordValidator(password);
    }

    @Test
    void build_withInvalidName_shouldThrowInputsException() throws Exception {
        // Arrange
        when(userValidator.nameValidator(null)).thenThrow(new InputsException("nombre no puede ser nulo"));

        // Act & Assert
        assertThrows(InputsException.class, () -> 
            userBuilder.build(null, "123", "30", "user", "pass")
        );
    }

    @Test
    void build_withInvalidDocument_shouldThrowInputsException() throws Exception {
        // Arrange
        doThrow(new InputsException("documento invalido")).when(userValidator).documentValidator("invalid");

        // Act & Assert
        assertThrows(InputsException.class, () -> 
            userBuilder.build("Juan", "invalid", "30", "user", "pass")
        );
    }

    @Test
    void build_withInvalidAge_shouldThrowInputsException() throws Exception {
        // Arrange
        doThrow(new InputsException("edad invalida")).when(userValidator).ageValidator("abc");

        // Act & Assert
        assertThrows(InputsException.class, () -> 
            userBuilder.build("Juan", "123", "abc", "user", "pass")
        );
    }

    @Test
    void build_withEmptyUsername_shouldThrowInputsException() throws Exception {
        // Arrange
        when(userValidator.userNameValidator("")).thenThrow(new InputsException("username vacio"));

        // Act & Assert
        assertThrows(InputsException.class, () -> 
            userBuilder.build("Juan", "123", "30", "", "pass")
        );
    }

    @Test
    void build_withNullPassword_shouldThrowInputsException() throws Exception {
        // Arrange
        when(userValidator.passwordValidator(null)).thenThrow(new InputsException("password nulo"));

        // Act & Assert
        assertThrows(InputsException.class, () -> 
            userBuilder.build("Juan", "123", "30", "user", null)
        );
    }
}
