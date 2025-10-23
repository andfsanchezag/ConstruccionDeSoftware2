package app.adapter.out.persistence;

import app.domain.model.Invoice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class InvoiceAdapterTest {

    @InjectMocks
    private InvoiceAdapter invoiceAdapter;

    private Invoice invoice;

    @BeforeEach
    void setUp() {
        invoice = new Invoice();
        invoice.setId(1L);
        invoice.setMedicine(true);
        invoice.setProductName("Test invoice");
        invoice.setProductAmount(100.0);
    }

    @Test
    void save_shouldNotThrowException() {
        // This adapter only prints to console, doesn't interact with repository
        
        // Act & Assert
        assertDoesNotThrow(() -> invoiceAdapter.save(invoice));
    }

    @Test
    void save_withNullInvoice_shouldNotThrowException() {
        // Act & Assert
        assertDoesNotThrow(() -> invoiceAdapter.save(null));
    }

    @Test
    void save_withDifferentInvoiceTypes_shouldNotThrowException() {
        // Test with medicine invoice
        invoice.setMedicine(true);
        assertDoesNotThrow(() -> invoiceAdapter.save(invoice));

        // Test with non-medicine invoice
        invoice.setMedicine(false);
        assertDoesNotThrow(() -> invoiceAdapter.save(invoice));
    }
}
