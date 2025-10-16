package app.adapter.in.rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.adapter.in.builder.UserBuilder;
import app.adapter.in.rest.request.UserRequest;
import app.application.exceptions.BusinessException;
import app.application.exceptions.InputsException;
import app.application.usecases.AdminUseCase;
import app.domain.model.User;
import app.domain.model.emuns.Role;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
	@Autowired
	private UserBuilder userBuilder;
	@Autowired
	private AdminUseCase adminUseCase;

	@PostMapping("/seller")
        @PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> createSeller(@RequestBody UserRequest request) {
	    try {
	        User user = userBuilder.build(
	                request.getName(),
	                request.getDocument(),
	                request.getAge(),
	                request.getUserName(),
	                request.getPassword()
	        );

	        adminUseCase.createSeller(user);

	        return ResponseEntity.status(HttpStatus.CREATED).body(user);

	    } catch (InputsException ie) {
	        return ResponseEntity
	                .status(HttpStatus.BAD_REQUEST)
	                .body(ie.getMessage());

	    } catch (BusinessException be) {
	        return ResponseEntity
	                .status(HttpStatus.CONFLICT)
	                .body(be.getMessage());

	    } catch (Exception e) {
	        return ResponseEntity
	                .status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body(e.getMessage());
	    }
	}


	@PostMapping("/veterinarian")
        @PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> createVeterinarian(@RequestBody UserRequest request) {
	    try {
	        User user = userBuilder.build(
	                request.getName(),
	                request.getDocument(),
	                request.getAge(),
	                request.getUserName(),
	                request.getPassword()
	        );

	        adminUseCase.createVeterinarian(user);

	        return ResponseEntity.status(HttpStatus.CREATED).body(user);

	    } catch (InputsException ie) {
	        return ResponseEntity
	                .status(HttpStatus.BAD_REQUEST)
	                .body(ie.getMessage());

	    } catch (BusinessException be) {
	        return ResponseEntity
	                .status(HttpStatus.CONFLICT)
	                .body(be.getMessage());

	    } catch (Exception e) {
	        return ResponseEntity
	                .status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body(e.getMessage());
	    }
	}


}
