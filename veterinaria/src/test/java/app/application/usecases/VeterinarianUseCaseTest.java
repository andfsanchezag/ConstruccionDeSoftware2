package app.application.usecases;

import app.domain.model.ClinicalOrder;
import app.domain.model.ClinicalRecord;
import app.domain.model.Pet;
import app.domain.model.User;
import app.domain.model.emuns.Role;
import app.domain.services.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VeterinarianUseCaseTest {

    @Mock
    private CreateUser createUser;

    @Mock
    private CreatePet createPet;

    @Mock
    private CreateClinicalOrder createClinicalOrder;

    @Mock
    private SearchClinicalOrderByPet searchClinicalOrder;

    @Mock
    private CreateClinicalRecord createClinicalRecord;

    @InjectMocks
    private VeterinarianUseCase veterinarianUseCase;

    private User testUser;
    private Pet testPet;
    private ClinicalOrder testOrder;
    private ClinicalRecord testRecord;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setDocument(123456L);
        testUser.setName("Owner");

        testPet = new Pet();
        testPet.setName("Rex");
        testPet.setId(10L);

        testOrder = new ClinicalOrder();
        testOrder.setId(100L);

        testRecord = new ClinicalRecord();
        testRecord.setId(500L);
    }

    @Test
    void createOwner_shouldSetOwnerRoleAndCallCreateUser() throws Exception {
        // Act
        veterinarianUseCase.CreateOwner(testUser);

        // Assert
        assertEquals(Role.OWNER, testUser.getRole());
        verify(createUser).create(testUser);
    }

    @Test
    void createOwner_withExistingRole_shouldOverrideToOwner() throws Exception {
        // Arrange
        testUser.setRole(Role.ADMIN);

        // Act
        veterinarianUseCase.CreateOwner(testUser);

        // Assert
        assertEquals(Role.OWNER, testUser.getRole());
    }

    @Test
    void createPet_shouldCallCreatePetService() throws Exception {
        // Act
        veterinarianUseCase.CreatePet(testPet);

        // Assert
        verify(createPet).create(testPet);
    }

    @Test
    void createPet_whenServiceThrows_shouldPropagateException() throws Exception {
        // Arrange
        doThrow(new Exception("Pet creation failed")).when(createPet).create(testPet);

        // Act & Assert
        assertThrows(Exception.class, () -> 
            veterinarianUseCase.CreatePet(testPet)
        );
    }

    @Test
    void createOrder_shouldCallCreateClinicalOrderService() throws Exception {
        // Act
        veterinarianUseCase.createOrder(testOrder);

        // Assert
        verify(createClinicalOrder).create(testOrder);
    }

    @Test
    void createOrder_whenServiceThrows_shouldPropagateException() throws Exception {
        // Arrange
        doThrow(new Exception("Order creation failed")).when(createClinicalOrder).create(testOrder);

        // Act & Assert
        assertThrows(Exception.class, () -> 
            veterinarianUseCase.createOrder(testOrder)
        );
    }

    @Test
    void searchOrders_shouldReturnOrdersList() throws Exception {
        // Arrange
        List<ClinicalOrder> orders = Arrays.asList(testOrder);
        when(searchClinicalOrder.search(testPet)).thenReturn(orders);

        // Act
        List<ClinicalOrder> result = veterinarianUseCase.searchOrders(testPet);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(searchClinicalOrder).search(testPet);
    }

    @Test
    void searchOrders_whenNoOrders_shouldReturnEmptyList() throws Exception {
        // Arrange
        when(searchClinicalOrder.search(testPet)).thenReturn(Arrays.asList());

        // Act
        List<ClinicalOrder> result = veterinarianUseCase.searchOrders(testPet);

        // Assert
        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    void createClinicalRecord_shouldCallCreateClinicalRecordService() throws Exception {
        // Act
        veterinarianUseCase.createClinicalRecord(testRecord);

        // Assert
        verify(createClinicalRecord).create(testRecord);
    }

    @Test
    void createClinicalRecord_whenServiceThrows_shouldPropagateException() throws Exception {
        // Arrange
        doThrow(new Exception("Record creation failed")).when(createClinicalRecord).create(testRecord);

        // Act & Assert
        assertThrows(Exception.class, () -> 
            veterinarianUseCase.createClinicalRecord(testRecord)
        );
    }
}
