package com.navulia.genericerrorhandler.exception;

import com.navulia.genericerrorhandler.dto.ErrorResponseDto;
import com.navulia.genericerrorhandler.utility.ErrorMessages;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void handleResourceNotFoundException() {
        // Create a sample ResourceNotFoundException
        ResourceNotFoundException ex=new ResourceNotFoundException(ErrorMessages.ERROR_RESOURCE_NOT_FOUND + 12);
        // Define the expected response
        ResponseEntity<ErrorResponseDto> expectedResponse = ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDto(HttpStatus.NOT_FOUND.value(),
                ex.getMessage()));
        //Define Actual response
        ResponseEntity<ErrorResponseDto> actualResponse = globalExceptionHandler.handleResourceNotFoundException(ex);
        // Call the method and assert the response
        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
        ErrorResponseDto expectedResultBody = expectedResponse.getBody();
        ErrorResponseDto actualResultBody = actualResponse.getBody();
        if(expectedResultBody != null && actualResultBody !=null) {
            assertEquals(expectedResultBody.getMessage(), actualResultBody.getMessage());
        }

    }

    @Test
    void handleMethodArgumentTypeMismatchException() {
        MethodArgumentTypeMismatchException ex = mock(MethodArgumentTypeMismatchException.class);
        when(ex.getMessage()).thenReturn("Given request param is not supported");
        // Call the method and assert the response
        ResponseEntity<ErrorResponseDto> actualResult = globalExceptionHandler.handleMethodArgumentTypeMismatchException(ex);
        assertEquals(HttpStatus.METHOD_NOT_ALLOWED, actualResult.getStatusCode());
        ErrorResponseDto actualResultBody = actualResult.getBody();
        if(actualResultBody != null) {
            assertEquals("Given request param is not supported", actualResult.getBody().getMessage());
        }
    }

    @Test
    void handleRequestMethodNotSupportedException() {
        HttpRequestMethodNotSupportedException ex = mock(HttpRequestMethodNotSupportedException.class);
        when(ex.getMessage()).thenReturn("Request method is not supported");
        // Call the method and assert the response
        ResponseEntity<ErrorResponseDto> actualResult = globalExceptionHandler.handleRequestMethodNotSupportedException(ex);
        assertEquals(HttpStatus.METHOD_NOT_ALLOWED, actualResult.getStatusCode());
        ErrorResponseDto actualResultBody = actualResult.getBody();
        if(actualResultBody != null) {
            assertEquals("Request method is not supported", actualResult.getBody().getMessage());
        }
    }


    @Test
    void handleHttpMessageNotReadableException() {
        HttpMessageNotReadableException ex = mock(HttpMessageNotReadableException.class);
        when(ex.getMessage()).thenReturn("Could not read JSON");
        // Call the method and assert the response
        ResponseEntity<ErrorResponseDto> actualResult = globalExceptionHandler.handleHttpMessageNotReadableException(ex);
        assertEquals(HttpStatus.BAD_REQUEST, actualResult.getStatusCode());
        ErrorResponseDto actualResultBody = actualResult.getBody();
        if(actualResultBody != null) {
            assertEquals("Could not read JSON", actualResult.getBody().getMessage());
        }

    }

    @Test
    void handleNoHandlerFoundException() {
        NoHandlerFoundException ex = mock(NoHandlerFoundException.class);
        when(ex.getRequestURL()).thenReturn("Request URL is not found");
        // Call the method and assert the response
        ResponseEntity<ErrorResponseDto> actualResult = globalExceptionHandler.handleNoHandlerFoundException(ex);
        assertEquals(HttpStatus.NOT_FOUND, actualResult.getStatusCode());
        ErrorResponseDto actualResultBody = actualResult.getBody();
        if(actualResultBody != null) {
            assertEquals("Request URL is not found", actualResult.getBody().getMessage());
        }
    }

    @Test
    void handleUnsupportedMediaTypeException() {
        HttpMediaTypeNotSupportedException ex = mock(HttpMediaTypeNotSupportedException.class);
        when(ex.getContentType()).thenReturn(MediaType.valueOf("application/json"));
        // Call the method and assert the response
        ResponseEntity<ErrorResponseDto> actualResult = globalExceptionHandler.handleUnsupportedMediaTypeException(ex);
        assertEquals(HttpStatus.UNSUPPORTED_MEDIA_TYPE, actualResult.getStatusCode());
        ErrorResponseDto actualResultBody = actualResult.getBody();
        if(actualResultBody != null) {
            assertEquals("application/json", actualResult.getBody().getMessage());
        }
    }

    @Test
    void handleCommonException(){
        Exception ex = mock(Exception.class);
        when(ex.getMessage()).thenReturn("Internal Server Error");
        // Call the method and assert the response
        ResponseEntity<ErrorResponseDto> actualResult = globalExceptionHandler.handleCommonException(ex);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actualResult.getStatusCode());
        ErrorResponseDto actualResultBody = actualResult.getBody();
        if(actualResultBody != null) {
            assertEquals("Internal Server Error", actualResult.getBody().getMessage());
        }
    }

}