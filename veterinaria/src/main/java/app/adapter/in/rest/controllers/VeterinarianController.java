package app.adapter.in.rest.controllers;

import app.adapter.rest.mapper.ClinicalOrderRestMapper;
import app.adapter.rest.mapper.ClinicalRecordRestMapper;
import app.adapter.rest.mapper.PetRestMapper;
import app.adapter.rest.mapper.UserRestMapper;
import app.adapter.rest.request.ClinicalOrderRequest;
import app.adapter.rest.request.ClinicalRecordRequest;
import app.adapter.rest.request.CreateUserRequest;
import app.adapter.rest.request.PetRequest;
import app.adapter.rest.response.ClinicalOrderResponse;
import app.adapter.rest.response.ClinicalRecordResponse;
import app.adapter.rest.response.PetResponse;
import app.adapter.rest.response.UserResponse;
import app.application.usecases.VeterinarianUseCase;
import app.domain.model.ClinicalOrder;
import app.domain.model.ClinicalRecord;
import app.domain.model.Pet;
import app.domain.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/veterinarian")
@PreAuthorize("hasRole('VETERINARIAN')")
public class VeterinarianController {

    @Autowired
    private VeterinarianUseCase veterinarianUseCase;

    @Autowired
    private UserRestMapper userRestMapper;
    @Autowired
    private PetRestMapper petRestMapper;
    @Autowired
    private ClinicalOrderRestMapper clinicalOrderRestMapper;
    @Autowired
    private ClinicalRecordRestMapper clinicalRecordRestMapper;

    @PostMapping("/owners")
    public ResponseEntity<UserResponse> createOwner(@RequestBody CreateUserRequest request) throws Exception {
        User user = userRestMapper.toDomain(request);
        veterinarianUseCase.CreateOwner(user);
        return new ResponseEntity<>(userRestMapper.toResponse(user), HttpStatus.CREATED);
    }

    @PostMapping("/pets")
    public ResponseEntity<PetResponse> createPet(@RequestBody PetRequest request) throws Exception {
        Pet pet = petRestMapper.toDomain(request);
        veterinarianUseCase.CreatePet(pet);
        return new ResponseEntity<>(petRestMapper.toResponse(pet), HttpStatus.CREATED);
    }

    @PostMapping("/orders")
    public ResponseEntity<ClinicalOrderResponse> createOrder(@RequestBody ClinicalOrderRequest request) throws Exception {
        ClinicalOrder order = clinicalOrderRestMapper.toDomain(request);
        veterinarianUseCase.createOrder(order);
        return new ResponseEntity<>(clinicalOrderRestMapper.toResponse(order), HttpStatus.CREATED);
    }

    @PostMapping("/orders/search")
    public ResponseEntity<List<ClinicalOrderResponse>> searchOrders(@RequestParam("petId") String petId) throws Exception {
        Pet pet = new Pet();
        pet.setId(Long.parseLong(petId));
        List<ClinicalOrder> orders = veterinarianUseCase.searchOrders(pet);
        List<ClinicalOrderResponse> res = orders.stream()
                .map(clinicalOrderRestMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(res);
    }

    @PostMapping("/records")
    public ResponseEntity<ClinicalRecordResponse> createClinicalRecord(@RequestBody ClinicalRecordRequest request) throws Exception {
        ClinicalRecord record = clinicalRecordRestMapper.toDomain(request);
        veterinarianUseCase.createClinicalRecord(record);
        return new ResponseEntity<>(clinicalRecordRestMapper.toResponse(record), HttpStatus.CREATED);
    }
}
