package app.adapter.rest.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.adapter.in.builder.ClinicalOrderBuilder;
import app.adapter.rest.request.ClinicalOrderRequest;
import app.adapter.rest.response.ClinicalOrderResponse;
import app.domain.model.ClinicalOrder;

@Component
public class ClinicalOrderRestMapper {
    @Autowired
    private ClinicalOrderBuilder clinicalOrderBuilder;

    public ClinicalOrder toDomain(ClinicalOrderRequest req) throws Exception {
        return clinicalOrderBuilder.builder(
            req.getVeterinarianDocument(),
            req.getPetId(),
            req.getMedicine(),
            req.getDoce()
        );
    }

    public ClinicalOrderResponse toResponse(ClinicalOrder order) {
        ClinicalOrderResponse res = new ClinicalOrderResponse();
        res.setId(order.getId());
        res.setPetId(order.getPet() != null ? order.getPet().getId() : 0);
        res.setOwnerDocument(order.getOwner() != null ? order.getOwner().getDocument() : 0);
        res.setVeterinarianDocument(order.getVeterinarian() != null ? order.getVeterinarian().getDocument() : 0);
        res.setMedicine(order.getMedicine());
        res.setDoce(order.getDoce());
        res.setDate(order.getDate());
        return res;
    }
}
