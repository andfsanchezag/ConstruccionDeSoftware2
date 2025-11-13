package app.application.usecases;

import app.domain.model.ClinicalOrder;
import app.domain.model.Invoice;
import app.domain.model.Pet;
import app.domain.services.CreateInvoice;
import app.domain.services.SearchClinicalOrderByPet;
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
class SellerUseCaseTest {

    @Mock
    private CreateInvoice createInvoice;

    @Mock
    private SearchClinicalOrderByPet searchClinicalOrderByPet;

    @InjectMocks
    private SellerUseCase sellerUseCase;

    private Invoice testInvoice;
    private Pet testPet;
    private List<ClinicalOrder> orders;

    @BeforeEach
    void setUp() {
        testInvoice = new Invoice();
        testInvoice.setProductName("Product");
        testInvoice.setProductAmount(100.0);

        testPet = new Pet();
        testPet.setId(10L);

        ClinicalOrder order1 = new ClinicalOrder();
        order1.setId(1L);
        ClinicalOrder order2 = new ClinicalOrder();
        order2.setId(2L);
        orders = Arrays.asList(order1, order2);
    }

    @Test
    void createInvoice_shouldCallCreateInvoiceService() throws Exception {
        // Act
        sellerUseCase.CreateInvoice(testInvoice);

        // Assert
        verify(createInvoice).create(testInvoice);
    }

    @Test
    void createInvoice_whenServiceThrows_shouldPropagateException() throws Exception {
        // Arrange
        doThrow(new Exception("Invoice creation failed")).when(createInvoice).create(testInvoice);

        // Act & Assert
        assertThrows(Exception.class, () -> 
            sellerUseCase.CreateInvoice(testInvoice)
        );
    }

    @Test
    void searchClinicalOrder_shouldReturnOrdersList() throws Exception {
        // Arrange
        when(searchClinicalOrderByPet.search(testPet)).thenReturn(orders);

        // Act
        List<ClinicalOrder> result = sellerUseCase.searchClinicalOrder(testPet);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(searchClinicalOrderByPet).search(testPet);
    }

    @Test
    void searchClinicalOrder_whenNoOrders_shouldReturnEmptyList() throws Exception {
        // Arrange
        when(searchClinicalOrderByPet.search(testPet)).thenReturn(Arrays.asList());

        // Act
        List<ClinicalOrder> result = sellerUseCase.searchClinicalOrder(testPet);

        // Assert
        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    void searchClinicalOrder_whenServiceThrows_shouldPropagateException() throws Exception {
        // Arrange
        when(searchClinicalOrderByPet.search(testPet)).thenThrow(new Exception("Search failed"));

        // Act & Assert
        assertThrows(Exception.class, () -> 
            sellerUseCase.searchClinicalOrder(testPet)
        );
    }
}
