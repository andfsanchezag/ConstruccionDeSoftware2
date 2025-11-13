package app.domain.services;

import app.domain.model.ClinicalOrder;
import app.domain.model.Pet;
import app.domain.ports.ClinicalOrderPort;
import app.domain.ports.PetPort;
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
class SearchClinicalOrderByPetTest {

    @Mock
    private PetPort petPort;

    @Mock
    private ClinicalOrderPort clinicalOrderPort;

    @InjectMocks
    private SearchClinicalOrderByPet searchClinicalOrderByPet;

    private Pet testPet;
    private List<ClinicalOrder> orders;

    @BeforeEach
    void setUp() {
        testPet = new Pet();
        testPet.setId(10L);
        testPet.setName("Rex");

        ClinicalOrder order1 = new ClinicalOrder();
        order1.setId(1L);
        ClinicalOrder order2 = new ClinicalOrder();
        order2.setId(2L);
        orders = Arrays.asList(order1, order2);
    }

    @Test
    void search_withValidPet_shouldReturnOrders() throws Exception {
        // Arrange
        when(petPort.findById(testPet)).thenReturn(testPet);
        when(clinicalOrderPort.findByPet(testPet)).thenReturn(orders);

        // Act
        List<ClinicalOrder> result = searchClinicalOrderByPet.search(testPet);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(petPort).findById(testPet);
        verify(clinicalOrderPort).findByPet(testPet);
    }

    @Test
    void search_withNonExistentPet_shouldThrowException() throws Exception {
        // Arrange
        when(petPort.findById(testPet)).thenReturn(null);

        // Act & Assert
        Exception ex = assertThrows(Exception.class, () -> 
            searchClinicalOrderByPet.search(testPet)
        );
        assertTrue(ex.getMessage().contains("mascota registrada"));
        verify(petPort).findById(testPet);
        verify(clinicalOrderPort, never()).findByPet(any());
    }

    @Test
    void search_withPetWithoutOrders_shouldReturnEmptyList() throws Exception {
        // Arrange
        when(petPort.findById(testPet)).thenReturn(testPet);
        when(clinicalOrderPort.findByPet(testPet)).thenReturn(Arrays.asList());

        // Act
        List<ClinicalOrder> result = searchClinicalOrderByPet.search(testPet);

        // Assert
        assertNotNull(result);
        assertEquals(0, result.size());
    }
}
