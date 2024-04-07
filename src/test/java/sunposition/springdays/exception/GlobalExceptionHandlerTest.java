package sunposition.springdays.exception;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {
    @Test
    void testHandleCustomNotFoundException() {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        RuntimeException ex = mock(HttpErrorExceptions.CustomNotFoundException.class);
        when(ex.getMessage()).thenReturn("Resource not found");

        ResponseEntity<Object> response = handler.handleCustomExceptions(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Resource not found", ((ErrorResponse) response.getBody()).getMessage());
    }

    @Test
    void testHandleCustomBadRequestException() {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        RuntimeException ex = mock(HttpErrorExceptions.CustomBadRequestException.class);
        when(ex.getMessage()).thenReturn("Bad request");

        ResponseEntity<Object> response = handler.handleCustomExceptions(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Bad request", ((ErrorResponse) response.getBody()).getMessage());
    }

    @Test
    void testHandleCustomMethodNotAllowedException() {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        RuntimeException ex = mock(HttpErrorExceptions.CustomMethodNotAllowedException.class);
        when(ex.getMessage()).thenReturn("Method not allowed");

        ResponseEntity<Object> response = handler.handleCustomExceptions(ex);

        assertEquals(HttpStatus.METHOD_NOT_ALLOWED, response.getStatusCode());
        assertEquals("Method not allowed", ((ErrorResponse) response.getBody()).getMessage());
    }

    @Test
    void testHandleCustomServiceUnavailableException() {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        RuntimeException ex = new HttpErrorExceptions.CustomServiceUnavailableException("Service unavailable");

        ResponseEntity<Object> response = handler.handleCustomExceptions(ex);

        assertEquals(HttpStatus.SERVICE_UNAVAILABLE, response.getStatusCode());
        assertEquals("Service unavailable", ((ErrorResponse) response.getBody()).getMessage());
    }

    @Test
    void testHandleCustomInternalServerErrorException() {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        RuntimeException ex = mock(HttpErrorExceptions.CustomInternalServerErrorException.class);
        when(ex.getMessage()).thenReturn("Internal server error");

        ResponseEntity<Object> response = handler.handleCustomExceptions(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Internal server error", ((ErrorResponse) response.getBody()).getMessage());
    }

}
