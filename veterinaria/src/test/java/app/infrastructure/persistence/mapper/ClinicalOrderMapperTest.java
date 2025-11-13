package app.infrastructure.persistence.mapper;

import app.domain.model.ClinicalOrder;
import app.domain.model.Pet;
import app.domain.model.User;
import app.infrastructure.persistence.entities.ClinicalOrderEntity;
import app.infrastructure.persistence.entities.PetEntity;
import app.infrastructure.persistence.entities.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

class ClinicalOrderMapperTest {

    private ClinicalOrder order;
    private ClinicalOrderEntity orderEntity;
    private Pet pet;
    private PetEntity petEntity;
    private User owner;
    private UserEntity ownerEntity;
    private User veterinarian;
    private UserEntity veterinarianEntity;
    private Date testDate;

    @BeforeEach
    void setUp() {
        testDate = new Date(System.currentTimeMillis());

        owner = new User();
        owner.setId(1L);
        owner.setDocument(111111L);

        ownerEntity = new UserEntity();
        ownerEntity.setId(1L);
        ownerEntity.setDocument(111111L);

        veterinarian = new User();
        veterinarian.setId(2L);
        veterinarian.setDocument(222222L);

        veterinarianEntity = new UserEntity();
        veterinarianEntity.setId(2L);
        veterinarianEntity.setDocument(222222L);

        pet = new Pet();
        pet.setId(10L);

        petEntity = new PetEntity();
        petEntity.setId(10L);

        order = new ClinicalOrder();
        order.setId(100L);
        order.setPet(pet);
        order.setOwner(owner);
        order.setVeterinarian(veterinarian);
        order.setMedicine("Antibiotic");
        order.setDoce("500mg");
        order.setDate(testDate);

        orderEntity = new ClinicalOrderEntity();
        orderEntity.setId(100L);
        orderEntity.setPet(petEntity);
        orderEntity.setOwner(ownerEntity);
        orderEntity.setVeterinarian(veterinarianEntity);
        orderEntity.setMedicine("Antibiotic");
        orderEntity.setDose("500mg");
        orderEntity.setDate(testDate);
    }

    @Test
    void toEntity_shouldMapAllFields() {
        // Act
        ClinicalOrderEntity result = ClinicalOrderMapper.toEntity(order);

        // Assert
        assertNotNull(result);
        assertEquals(100L, result.getId());
        assertEquals("Antibiotic", result.getMedicine());
        assertEquals("500mg", result.getDose());
        assertEquals(testDate, result.getDate());
        assertNotNull(result.getPet());
        assertEquals(10L, result.getPet().getId());
        assertNotNull(result.getOwner());
        assertEquals(111111L, result.getOwner().getDocument());
        assertNotNull(result.getVeterinarian());
        assertEquals(222222L, result.getVeterinarian().getDocument());
    }

    @Test
    void toEntity_withNullOrder_shouldReturnNull() {
        // Act
        ClinicalOrderEntity result = ClinicalOrderMapper.toEntity(null);

        // Assert
        assertNull(result);
    }

    @Test
    void toDomain_shouldMapAllFields() {
        // Act
        ClinicalOrder result = ClinicalOrderMapper.toDomain(orderEntity);

        // Assert
        assertNotNull(result);
        assertEquals(100L, result.getId());
        assertEquals("Antibiotic", result.getMedicine());
        assertEquals("500mg", result.getDoce());
        assertEquals(testDate, result.getDate());
        assertNotNull(result.getPet());
        assertEquals(10L, result.getPet().getId());
        assertNotNull(result.getOwner());
        assertEquals(111111L, result.getOwner().getDocument());
        assertNotNull(result.getVeterinarian());
        assertEquals(222222L, result.getVeterinarian().getDocument());
    }

    @Test
    void toDomain_withNullEntity_shouldReturnNull() {
        // Act
        ClinicalOrder result = ClinicalOrderMapper.toDomain(null);

        // Assert
        assertNull(result);
    }

    @Test
    void toEntity_shouldHandleDoseFieldCorrectly() {
        // The mapper converts "doce" (domain) to "dose" (entity)
        order.setDoce("250mg");

        // Act
        ClinicalOrderEntity result = ClinicalOrderMapper.toEntity(order);

        // Assert
        assertEquals("250mg", result.getDose());
    }

    @Test
    void toDomain_shouldHandleDoceFieldCorrectly() {
        // The mapper converts "dose" (entity) to "doce" (domain)
        orderEntity.setDose("250mg");

        // Act
        ClinicalOrder result = ClinicalOrderMapper.toDomain(orderEntity);

        // Assert
        assertEquals("250mg", result.getDoce());
    }
}
