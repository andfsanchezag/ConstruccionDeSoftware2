package app.adapter.in.builder;

import app.adapter.in.validators.InvoiceValidator;
import app.application.exceptions.InputsException;
import app.domain.model.Invoice;
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
class InvoiceBuilderTest {

    @Mock
    private InvoiceValidator invoiceValidator;

    @InjectMocks
    private InvoiceBuilder invoiceBuilder;

    @BeforeEach
    void setUp() {
        try {
            doAnswer(i -> {
                String s = i.getArgument(0);
                if (s == null || !s.matches("^-?\\d+$")) throw new InputsException("id mascota invalido");
                return Long.parseLong(s);
            }).when(invoiceValidator).petIdValidator(anyString());
            doAnswer(i -> {
                String s = i.getArgument(0);
                if (s == null || !s.matches("^-?\\d+$")) throw new InputsException("documento invalido");
                return Long.parseLong(s);
            }).when(invoiceValidator).ownerDocumentValidator(anyString());
            doAnswer(i -> {
                String s = i.getArgument(0);
                if (s == null || !s.matches("^-?\\d*(?:\\.\\d+)?$")) throw new InputsException("monto invalido");
                return Double.parseDouble(s);
            }).when(invoiceValidator).amountValidator(anyString());
            when(invoiceValidator.productNameValidator(anyString())).thenAnswer(i -> i.getArgument(0));
            doAnswer(i -> {
                String s = i.getArgument(0);
                return s != null && s.equalsIgnoreCase("si");
            }).when(invoiceValidator).isMedicineValidator(anyString());
            doAnswer(i -> {
                String s = i.getArgument(0);
                if (s == null || !s.matches("^-?\\d+$")) throw new InputsException("order id invalido");
                return Long.parseLong(s);
            }).when(invoiceValidator).orderIdValidator(anyString());
        } catch (Exception e) {
            fail("Setup failed");
        }
    }

    @Test
    void build_withMedicineAndOrder_shouldReturnInvoiceWithOrder() throws Exception {
        // Arrange
        String petId = "10";
        String ownerDocument = "12345";
        String productAmount = "150.75";
        String productName = "Antibiotico";
        String isMedicine = "si";
        String orderId = "500";

        // Act
        Invoice result = invoiceBuilder.build(petId, ownerDocument, productAmount, productName, isMedicine, orderId);

        // Assert
        assertNotNull(result);
        assertEquals(10L, result.getPet().getId());
        assertEquals(12345L, result.getOwner().getDocument());
        assertEquals(150.75, result.getProductAmount(), 0.001);
        assertEquals("Antibiotico", result.getProductName());
        assertTrue(result.isMedicine());
        assertNotNull(result.getOrder());
        assertEquals(500L, result.getOrder().getId());
        assertNotNull(result.getDate());

        verify(invoiceValidator).petIdValidator(petId);
        verify(invoiceValidator).ownerDocumentValidator(ownerDocument);
        verify(invoiceValidator).amountValidator(productAmount);
        verify(invoiceValidator).productNameValidator(productName);
        verify(invoiceValidator).isMedicineValidator(isMedicine);
        verify(invoiceValidator).orderIdValidator(orderId);
    }

    @Test
    void build_withoutMedicine_shouldReturnInvoiceWithoutOrder() throws Exception {
        // Arrange
        String petId = "20";
        String ownerDocument = "54321";
        String productAmount = "50.00";
        String productName = "Food";
        String isMedicine = "no";
        String orderId = null;

        // Act
        Invoice result = invoiceBuilder.build(petId, ownerDocument, productAmount, productName, isMedicine, orderId);

        // Assert
        assertNotNull(result);
        assertEquals(20L, result.getPet().getId());
        assertEquals(54321L, result.getOwner().getDocument());
        assertEquals(50.00, result.getProductAmount(), 0.001);
        assertEquals("Food", result.getProductName());
        assertFalse(result.isMedicine());
        assertNull(result.getOrder());

        verify(invoiceValidator).petIdValidator(petId);
        verify(invoiceValidator).ownerDocumentValidator(ownerDocument);
        verify(invoiceValidator).amountValidator(productAmount);
        verify(invoiceValidator).productNameValidator(productName);
        verify(invoiceValidator).isMedicineValidator(isMedicine);
        verify(invoiceValidator, never()).orderIdValidator(any());
    }

    @Test
    void build_withInvalidPetId_shouldThrowInputsException() throws Exception {
        // Arrange
        doThrow(new InputsException("id mascota invalido")).when(invoiceValidator).petIdValidator("invalid");

        // Act & Assert
        assertThrows(InputsException.class, () -> 
            invoiceBuilder.build("invalid", "123", "100", "Product", "no", null)
        );
    }

    @Test
    void build_withInvalidAmount_shouldThrowInputsException() throws Exception {
        // Arrange
        doThrow(new InputsException("monto invalido")).when(invoiceValidator).amountValidator("abc");

        // Act & Assert
        assertThrows(InputsException.class, () -> 
            invoiceBuilder.build("10", "123", "abc", "Product", "no", null)
        );
    }

    @Test
    void build_withNullProductName_shouldThrowInputsException() throws Exception {
        // Arrange
        doThrow(new InputsException("nombre nulo")).when(invoiceValidator).productNameValidator((String) null);

        // Act & Assert
        assertThrows(InputsException.class, () -> 
            invoiceBuilder.build("10", "123", "100", null, "no", null)
        );
    }

    @Test
    void build_withInvalidOrderId_shouldThrowInputsException() throws Exception {
        // Arrange
        doThrow(new InputsException("order id invalido")).when(invoiceValidator).orderIdValidator("xyz");

        // Act & Assert
        assertThrows(InputsException.class, () -> 
            invoiceBuilder.build("10", "123", "100", "Med", "si", "xyz")
        );
    }

    @Test
    void build_shouldSetCurrentDate() throws Exception {
        // Act
        Invoice result = invoiceBuilder.build("10", "123", "100", "Product", "no", null);

        // Assert
        assertNotNull(result.getDate());
    }
}
