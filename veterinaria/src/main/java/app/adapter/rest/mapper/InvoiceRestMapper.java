package app.adapter.rest.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.adapter.in.builder.InvoiceBuilder;
import app.adapter.rest.request.InvoiceRequest;
import app.adapter.rest.response.InvoiceResponse;
import app.domain.model.Invoice;

@Component
public class InvoiceRestMapper {
    @Autowired
    private InvoiceBuilder invoiceBuilder;

    public Invoice toDomain(InvoiceRequest req) throws Exception {
        return invoiceBuilder.build(
            req.getPetId(),
            req.getOwnerDocument(),
            req.getProductAmount(),
            req.getProductName(),
            req.getIsMedicine(),
            req.getOrderId()
        );
    }

    public InvoiceResponse toResponse(Invoice invoice) {
        InvoiceResponse res = new InvoiceResponse();
        res.setId(invoice.getId());
        res.setPetId(invoice.getPet() != null ? invoice.getPet().getId() : 0);
        res.setOwnerDocument(invoice.getOwner() != null ? invoice.getOwner().getDocument() : 0);
        res.setProductName(invoice.getProductName());
        res.setProductAmount(invoice.getProductAmount());
        res.setMedicine(invoice.isMedicine());
        res.setOrderId(invoice.getOrder() != null ? invoice.getOrder().getId() : null);
        res.setDate(invoice.getDate());
        return res;
    }
}
