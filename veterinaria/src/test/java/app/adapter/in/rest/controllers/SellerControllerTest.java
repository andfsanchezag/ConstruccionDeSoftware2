package app.adapter.in.rest.controllers;

import app.adapter.rest.mapper.ClinicalOrderRestMapper;
import app.adapter.rest.mapper.InvoiceRestMapper;
import app.adapter.rest.request.InvoiceRequest;
import app.adapter.rest.response.ClinicalOrderResponse;
import app.adapter.rest.response.InvoiceResponse;
import app.application.usecases.SellerUseCase;
import app.domain.model.ClinicalOrder;
import app.domain.model.Invoice;
import app.domain.model.Pet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SellerControllerTest {

    @Mock
    private SellerUseCase sellerUseCase;

    @Mock
    private InvoiceRestMapper invoiceRestMapper;

    @Mock
    private ClinicalOrderRestMapper clinicalOrderRestMapper;

    @InjectMocks
    private SellerController sellerController;

    private InvoiceRequest invoiceRequest;
    private Invoice invoice;
    private InvoiceResponse invoiceResponse;
    private List<ClinicalOrder> orders;
    private List<ClinicalOrderResponse> orderResponses;

    @BeforeEach
    void setUp() {
        invoiceRequest = new InvoiceRequest();
        invoiceRequest.setPetId("10");
        invoiceRequest.setProductName("Product");

        invoice = new Invoice();
        invoice.setId(1L);

        invoiceResponse = new InvoiceResponse();
        invoiceResponse.setId(1L);

        ClinicalOrder order1 = new ClinicalOrder();
        order1.setId(1L);
        orders = Arrays.asList(order1);

        ClinicalOrderResponse orderResp1 = new ClinicalOrderResponse();
        orderResp1.setId(1L);
        orderResponses = Arrays.asList(orderResp1);
    }

    @Test
    void createInvoice_shouldReturnCreated() throws Exception {
        // Arrange
        when(invoiceRestMapper.toDomain(invoiceRequest)).thenReturn(invoice);
        when(invoiceRestMapper.toResponse(invoice)).thenReturn(invoiceResponse);

        // Act
        ResponseEntity<InvoiceResponse> response = sellerController.createInvoice(invoiceRequest);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(sellerUseCase).CreateInvoice(invoice);
        verify(invoiceRestMapper).toResponse(invoice);
    }

    @Test
    void searchByPet_shouldReturnOrdersList() throws Exception {
        // Arrange
        when(sellerUseCase.searchClinicalOrder(any(Pet.class))).thenReturn(orders);
        when(clinicalOrderRestMapper.toResponse(any(ClinicalOrder.class))).thenReturn(orderResponses.get(0));

        // Act
        ResponseEntity<List<ClinicalOrderResponse>> response = sellerController.searchByPet("10");

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(sellerUseCase).searchClinicalOrder(any(Pet.class));
    }

    @Test
    void createInvoice_whenUseCaseThrows_shouldPropagateException() throws Exception {
        // Arrange
        when(invoiceRestMapper.toDomain(invoiceRequest)).thenReturn(invoice);
        doThrow(new Exception("Failed")).when(sellerUseCase).CreateInvoice(invoice);

        // Act & Assert
        assertThrows(Exception.class, () -> 
            sellerController.createInvoice(invoiceRequest)
        );
    }
}
