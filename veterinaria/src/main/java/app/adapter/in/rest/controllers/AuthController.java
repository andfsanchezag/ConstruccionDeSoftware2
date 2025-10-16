package app.adapter.in.rest.controllers;

import app.application.exceptions.BusinessException;
import app.application.exceptions.InputsException;
import app.domain.model.auth.AuthCredentials;
import app.domain.model.auth.TokenResponse;
import app.domain.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthCredentials credentials) {
    	try {
        TokenResponse response = authenticationService.authenticate(credentials);
        return ResponseEntity.ok(response);}
    	catch (InputsException ie) {
	        return ResponseEntity
	                .status(HttpStatus.BAD_REQUEST)
	                .body(ie.getMessage());

	    } catch (BusinessException be) {
	        return ResponseEntity
	                .status(HttpStatus.UNAUTHORIZED)
	                .body(be.getMessage());

	    } catch (Exception e) {
	        return ResponseEntity
	                .status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body(e.getMessage());
	    }
    }
}