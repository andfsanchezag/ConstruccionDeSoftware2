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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/seller")
@PreAuthorize("hasRole('SELLER')")
public class SellerController {

    @Autowired
    private SellerUseCase sellerUseCase;

    @Autowired
    private InvoiceRestMapper invoiceRestMapper;

    @Autowired
    private ClinicalOrderRestMapper clinicalOrderRestMapper;

    @PostMapping("/invoices")
    public ResponseEntity<InvoiceResponse> createInvoice(@RequestBody InvoiceRequest request) throws Exception {
        Invoice invoice = invoiceRestMapper.toDomain(request);
        sellerUseCase.CreateInvoice(invoice);
        return new ResponseEntity<>(invoiceRestMapper.toResponse(invoice), HttpStatus.CREATED);
    }

    @PostMapping("/orders/search")
    public ResponseEntity<List<ClinicalOrderResponse>> searchByPet(@RequestParam("petId") String petId) throws Exception {
        Pet pet = new Pet();
        pet.setId(Long.parseLong(petId));
        List<ClinicalOrder> orders = sellerUseCase.searchClinicalOrder(pet);
        List<ClinicalOrderResponse> res = orders.stream()
                .map(clinicalOrderRestMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(res);
    }
}
