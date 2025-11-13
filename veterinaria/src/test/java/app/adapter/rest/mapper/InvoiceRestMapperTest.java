package app.adapter.rest.mapper;

import app.adapter.in.builder.InvoiceBuilder;
import app.adapter.rest.request.InvoiceRequest;
import app.adapter.rest.response.InvoiceResponse;
import app.domain.model.ClinicalOrder;
import app.domain.model.Invoice;
import app.domain.model.Pet;
import app.domain.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InvoiceRestMapperTest {

    @Mock
    private InvoiceBuilder invoiceBuilder;

    @InjectMocks
    private InvoiceRestMapper invoiceRestMapper;

    private InvoiceRequest request;
    private Invoice invoice;

    @BeforeEach
    void setUp() {
        request = new InvoiceRequest();
        request.setPetId("10");
        request.setOwnerDocument("123456");
        request.setProductAmount("100.50");
        request.setProductName("Product X");
        request.setIsMedicine("si");
        request.setOrderId("500");

        Pet pet = new Pet();
        pet.setId(10L);
        
        User owner = new User();
        owner.setDocument(123456L);
        
        ClinicalOrder order = new ClinicalOrder();
        order.setId(500L);

        invoice = new Invoice();
        invoice.setId(1L);
        invoice.setPet(pet);
        invoice.setOwner(owner);
        invoice.setProductAmount(100.50);
        invoice.setProductName("Product X");
        invoice.setMedicine(true);
        invoice.setOrder(order);
        invoice.setDate(new Date(System.currentTimeMillis()));
    }

    @Test
    void toDomain_shouldCallBuilder() throws Exception {
        // Arrange
        when(invoiceBuilder.build(anyString(), anyString(), anyString(), anyString(), anyString(), anyString()))
            .thenReturn(invoice);

        // Act
        Invoice result = invoiceRestMapper.toDomain(request);

        // Assert
        assertNotNull(result);
        verify(invoiceBuilder).build("10", "123456", "100.50", "Product X", "si", "500");
    }

    @Test
    void toResponse_withMedicine_shouldMapAllFields() {
        // Act
        InvoiceResponse result = invoiceRestMapper.toResponse(invoice);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(10L, result.getPetId());
        assertEquals(123456L, result.getOwnerDocument());
        assertEquals(100.50, result.getProductAmount(), 0.001);
        assertEquals("Product X", result.getProductName());
        assertTrue(result.isMedicine());
        assertEquals(500L, result.getOrderId());
        assertNotNull(result.getDate());
    }

    @Test
    void toResponse_withoutMedicine_shouldMapOrderIdAsNull() {
        // Arrange
        invoice.setMedicine(false);
        invoice.setOrder(null);

        // Act
        InvoiceResponse result = invoiceRestMapper.toResponse(invoice);

        // Assert
        assertFalse(result.isMedicine());
        assertNull(result.getOrderId());
    }
}
