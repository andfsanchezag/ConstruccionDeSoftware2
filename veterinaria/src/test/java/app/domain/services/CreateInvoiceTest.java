package app.domain.services;

import app.domain.model.ClinicalOrder;
import app.domain.model.Invoice;
import app.domain.model.Pet;
import app.domain.model.User;
import app.domain.ports.ClinicalOrderPort;
import app.domain.ports.InvoicePort;
import app.domain.ports.PetPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateInvoiceTest {

    @Mock
    private PetPort petPort;

    @Mock
    private ClinicalOrderPort clinicalOrderPort;

    @Mock
    private InvoicePort invoicePort;

    @InjectMocks
    private CreateInvoice createInvoice;

    private Invoice testInvoice;
    private Pet pet;
    private User owner;
    private ClinicalOrder order;

    @BeforeEach
    void setUp() {
        owner = new User();
        owner.setDocument(123456L);

        pet = new Pet();
        pet.setId(10L);
        pet.setName("Rex");
        pet.setOwner(owner);

        order = new ClinicalOrder();
        order.setId(100L);
        order.setPet(pet);

        testInvoice = new Invoice();
        Pet petRef = new Pet();
        petRef.setId(10L);
        testInvoice.setPet(petRef);
        testInvoice.setProductName("Product");
        testInvoice.setProductAmount(100.0);
    }

    @Test
    void create_withNonMedicineProduct_shouldSaveInvoiceWithoutOrder() throws Exception {
        // Arrange
        testInvoice.setMedicine(false);
        when(petPort.findById(any(Pet.class))).thenReturn(pet);

        // Act
        createInvoice.create(testInvoice);

        // Assert
        assertEquals(pet, testInvoice.getPet());
        assertEquals(owner, testInvoice.getOwner());
        verify(petPort).findById(any(Pet.class));
        verify(clinicalOrderPort, never()).findById(any());
        verify(invoicePort).save(testInvoice);
    }

    @Test
    void create_withMedicineAndValidOrder_shouldSaveInvoiceWithOrder() throws Exception {
        // Arrange
        testInvoice.setMedicine(true);
        ClinicalOrder orderRef = new ClinicalOrder();
        orderRef.setId(100L);
        testInvoice.setOrder(orderRef);
        
        when(petPort.findById(any(Pet.class))).thenReturn(pet);
        when(clinicalOrderPort.findById(any(ClinicalOrder.class))).thenReturn(order);

        // Act
        createInvoice.create(testInvoice);

        // Assert
        assertEquals(pet, testInvoice.getPet());
        assertEquals(owner, testInvoice.getOwner());
        assertEquals(order, testInvoice.getOrder());
        verify(petPort).findById(any(Pet.class));
        verify(clinicalOrderPort).findById(any(ClinicalOrder.class));
        verify(invoicePort).save(testInvoice);
    }

    @Test
    void create_withNullPet_shouldThrowException() throws Exception {
        // Arrange
        when(petPort.findById(any(Pet.class))).thenReturn(null);

        // Act & Assert
        Exception ex = assertThrows(Exception.class, () -> 
            createInvoice.create(testInvoice)
        );
        assertTrue(ex.getMessage().contains("mascota asociada"));
        verify(invoicePort, never()).save(any());
    }

    @Test
    void create_withMedicineButNullOrder_shouldThrowException() throws Exception {
        // Arrange
        testInvoice.setMedicine(true);
        ClinicalOrder orderRef = new ClinicalOrder();
        orderRef.setId(100L);
        testInvoice.setOrder(orderRef);
        
        when(petPort.findById(any(Pet.class))).thenReturn(pet);
        when(clinicalOrderPort.findById(any(ClinicalOrder.class))).thenReturn(null);

        // Act & Assert
        Exception ex = assertThrows(Exception.class, () -> 
            createInvoice.create(testInvoice)
        );
        assertTrue(ex.getMessage().contains("orden asociada"));
        verify(invoicePort, never()).save(any());
    }

    @Test
    void create_withMedicineAndOrderForDifferentPet_shouldThrowException() throws Exception {
        // Arrange
        testInvoice.setMedicine(true);
        ClinicalOrder orderRef = new ClinicalOrder();
        orderRef.setId(100L);
        testInvoice.setOrder(orderRef);
        
        Pet differentPet = new Pet();
        differentPet.setId(999L); // Different pet ID
        order.setPet(differentPet);
        
        when(petPort.findById(any(Pet.class))).thenReturn(pet);
        when(clinicalOrderPort.findById(any(ClinicalOrder.class))).thenReturn(order);

        // Act & Assert
        Exception ex = assertThrows(Exception.class, () -> 
            createInvoice.create(testInvoice)
        );
        assertTrue(ex.getMessage().contains("orden asociada"));
        verify(invoicePort, never()).save(any());
    }

    @Test
    void create_shouldSetOwnerFromPet() throws Exception {
        // Arrange
        testInvoice.setMedicine(false);
        when(petPort.findById(any(Pet.class))).thenReturn(pet);

        // Act
        createInvoice.create(testInvoice);

        // Assert
        assertEquals(owner, testInvoice.getOwner());
        assertEquals(123456L, testInvoice.getOwner().getDocument());
    }
}
