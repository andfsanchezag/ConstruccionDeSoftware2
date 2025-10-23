package app.adapter.in.rest.controllers;

import app.application.exceptions.BusinessException;
import app.application.exceptions.InputsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    void setUp() {
        globalExceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    void handleInputs_shouldReturn400WithErrorMessage() {
        // Arrange
        InputsException ex = new InputsException("Invalid input value");

        // Act
        ResponseEntity<Map<String, String>> response = globalExceptionHandler.handleInputs(ex);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Invalid input value", response.getBody().get("error"));
    }

    @Test
    void handleBusiness_shouldReturn409WithErrorMessage() {
        // Arrange
        BusinessException ex = new BusinessException("Business rule violated");

        // Act
        ResponseEntity<Map<String, String>> response = globalExceptionHandler.handleBusiness(ex);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Business rule violated", response.getBody().get("error"));
    }

    @Test
    void handleUnexpected_shouldReturn500WithErrorMessage() {
        // Arrange
        Exception ex = new Exception("Unexpected error occurred");

        // Act
        ResponseEntity<Map<String, String>> response = globalExceptionHandler.handleUnexpected(ex);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Unexpected error occurred", response.getBody().get("error"));
    }

    @Test
    void handleInputs_withNullMessage_shouldHandleGracefully() {
        // Arrange
        InputsException ex = new InputsException(null);

        // Act
        ResponseEntity<Map<String, String>> response = globalExceptionHandler.handleInputs(ex);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody().get("error"));
    }

    @Test
    void handleBusiness_withNullMessage_shouldHandleGracefully() {
        // Arrange
        BusinessException ex = new BusinessException(null);

        // Act
        ResponseEntity<Map<String, String>> response = globalExceptionHandler.handleBusiness(ex);

        // Assert
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertNull(response.getBody().get("error"));
    }

    @Test
    void handleUnexpected_withRuntimeException_shouldReturn500() {
        // Arrange
        RuntimeException ex = new RuntimeException("Runtime error");

        // Act
        ResponseEntity<Map<String, String>> response = globalExceptionHandler.handleUnexpected(ex);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Runtime error", response.getBody().get("error"));
    }

    @Test
    void allHandlers_shouldReturnMapWithErrorKey() {
        // Test that all handlers return a map with "error" key
        InputsException inputsEx = new InputsException("Input error");
        BusinessException businessEx = new BusinessException("Business error");
        Exception unexpectedEx = new Exception("Unexpected error");

        ResponseEntity<Map<String, String>> inputsResp = globalExceptionHandler.handleInputs(inputsEx);
        ResponseEntity<Map<String, String>> businessResp = globalExceptionHandler.handleBusiness(businessEx);
        ResponseEntity<Map<String, String>> unexpectedResp = globalExceptionHandler.handleUnexpected(unexpectedEx);

        assertTrue(inputsResp.getBody().containsKey("error"));
        assertTrue(businessResp.getBody().containsKey("error"));
        assertTrue(unexpectedResp.getBody().containsKey("error"));
    }
}
