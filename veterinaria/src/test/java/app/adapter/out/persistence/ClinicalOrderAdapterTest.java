package app.adapter.out.persistence;

import app.domain.model.ClinicalOrder;
import app.domain.model.Pet;
import app.domain.model.User;
import app.infrastructure.persistence.entities.ClinicalOrderEntity;
import app.infrastructure.persistence.entities.PetEntity;
import app.infrastructure.persistence.repository.ClinicalOrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClinicalOrderAdapterTest {

    @Mock
    private ClinicalOrderRepository clinicalOrderRepository;

    @InjectMocks
    private ClinicalOrderAdapter clinicalOrderAdapter;

    private ClinicalOrder order;
    private ClinicalOrderEntity orderEntity;
    private Pet pet;
    private PetEntity petEntity;

    @BeforeEach
    void setUp() {
        pet = new Pet();
        pet.setId(10L);

        petEntity = new PetEntity();
        petEntity.setId(10L);

        User owner = new User();
        owner.setDocument(111111L);

        User veterinarian = new User();
        veterinarian.setDocument(222222L);

        order = new ClinicalOrder();
        order.setId(100L);
        order.setPet(pet);
        order.setOwner(owner);
        order.setVeterinarian(veterinarian);
        order.setMedicine("Antibiotic");
        order.setDoce("500mg");
        order.setDate(new Date(System.currentTimeMillis()));

        orderEntity = new ClinicalOrderEntity();
        orderEntity.setId(100L);
        orderEntity.setPet(petEntity);
        orderEntity.setMedicine("Antibiotic");
        orderEntity.setDose("500mg");
    }

    @Test
    void findById_shouldReturnClinicalOrder() throws Exception {
        // Arrange
        when(clinicalOrderRepository.findById(100L)).thenReturn(orderEntity);

        // Act
        ClinicalOrder result = clinicalOrderAdapter.findById(order);

        // Assert
        assertNotNull(result);
        assertEquals(100L, result.getId());
        assertEquals("Antibiotic", result.getMedicine());
        verify(clinicalOrderRepository).findById(100L);
    }

    @Test
    void findById_whenNotFound_shouldReturnNull() throws Exception {
        // Arrange
        when(clinicalOrderRepository.findById(100L)).thenReturn(null);

        // Act
        ClinicalOrder result = clinicalOrderAdapter.findById(order);

        // Assert
        assertNull(result);
        verify(clinicalOrderRepository).findById(100L);
    }

    @Test
    void findByPet_shouldReturnListOfOrders() throws Exception {
        // Arrange
        ClinicalOrderEntity order2 = new ClinicalOrderEntity();
        order2.setId(101L);
        order2.setMedicine("Painkiller");
        
        when(clinicalOrderRepository.findByPet(any(PetEntity.class)))
            .thenReturn(Arrays.asList(orderEntity, order2));

        // Act
        List<ClinicalOrder> result = clinicalOrderAdapter.findByPet(pet);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(100L, result.get(0).getId());
        assertEquals(101L, result.get(1).getId());
        verify(clinicalOrderRepository).findByPet(any(PetEntity.class));
    }

    @Test
    void findByPet_whenEmpty_shouldReturnEmptyList() throws Exception {
        // Arrange
        when(clinicalOrderRepository.findByPet(any(PetEntity.class)))
            .thenReturn(Arrays.asList());

        // Act
        List<ClinicalOrder> result = clinicalOrderAdapter.findByPet(pet);

        // Assert
        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    void save_shouldCallRepository() throws Exception {
        // Arrange
        when(clinicalOrderRepository.save(any(ClinicalOrderEntity.class)))
            .thenReturn(orderEntity);

        // Act
        clinicalOrderAdapter.save(order);

        // Assert
        verify(clinicalOrderRepository).save(any(ClinicalOrderEntity.class));
    }

    @Test
    void save_shouldConvertToEntityBeforeSaving() throws Exception {
        // Arrange
        when(clinicalOrderRepository.save(any(ClinicalOrderEntity.class)))
            .thenReturn(orderEntity);

        // Act
        clinicalOrderAdapter.save(order);

        // Assert
        verify(clinicalOrderRepository).save(argThat(entity ->
            entity.getId() == 100L &&
            entity.getMedicine().equals("Antibiotic") &&
            entity.getDose().equals("500mg")
        ));
    }

    @Test
    void findByPet_shouldConvertPetToEntity() throws Exception {
        // Arrange
        when(clinicalOrderRepository.findByPet(any(PetEntity.class)))
            .thenReturn(Arrays.asList(orderEntity));

        // Act
        clinicalOrderAdapter.findByPet(pet);

        // Assert
        verify(clinicalOrderRepository).findByPet(argThat(entity ->
            entity.getId() == 10L
        ));
    }
}
