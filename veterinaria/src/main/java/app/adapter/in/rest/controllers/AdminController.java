package app.adapter.in.rest.controllers;

import app.adapter.rest.mapper.UserRestMapper;
import app.adapter.rest.request.CreateUserRequest;
import app.adapter.rest.response.UserResponse;
import app.application.usecases.AdminUseCase;
import app.domain.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private AdminUseCase adminUseCase;

    @Autowired
    private UserRestMapper userRestMapper;

    @PostMapping("/users/veterinarian")
    public ResponseEntity<UserResponse> createVeterinarian(@RequestBody CreateUserRequest request) throws Exception {
        User user = userRestMapper.toDomain(request);
        adminUseCase.createVeterinarian(user);
        return new ResponseEntity<>(userRestMapper.toResponse(user), HttpStatus.CREATED);
    }

    @PostMapping("/users/seller")
    public ResponseEntity<UserResponse> createSeller(@RequestBody CreateUserRequest request) throws Exception {
        User user = userRestMapper.toDomain(request);
        adminUseCase.createSeller(user);
        return new ResponseEntity<>(userRestMapper.toResponse(user), HttpStatus.CREATED);
    }
}

