package sunposition.springdays.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger GLOBAL_EXCEPTION_HANDLER_LOGGER =
            LogManager.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = {
            HttpErrorExceptions.CustomNotFoundException.class,
            HttpErrorExceptions.CustomBadRequestException.class,
            HttpErrorExceptions.CustomMethodNotAllowedException.class,
            HttpErrorExceptions.CustomServiceUnavailableException.class
    })
    public ResponseEntity<Object> handleCustomExceptions(
            final RuntimeException ex) {
        HttpStatus status = null;
        if (ex instanceof HttpErrorExceptions.CustomNotFoundException) {
            status = HttpStatus.NOT_FOUND;
        } else if (ex instanceof HttpErrorExceptions.
                CustomBadRequestException) {
            status = HttpStatus.BAD_REQUEST;
        } else if (ex instanceof HttpErrorExceptions.
                CustomMethodNotAllowedException) {
            status = HttpStatus.METHOD_NOT_ALLOWED;
        } else if (ex instanceof HttpErrorExceptions.
                CustomServiceUnavailableException) {
            status = HttpStatus.SERVICE_UNAVAILABLE;
        } else if (ex instanceof HttpErrorExceptions.
                CustomInternalServerErrorException) {
            GLOBAL_EXCEPTION_HANDLER_LOGGER.error(
                    "CustomInternalServerErrorException occurred: {}",
                    ex.getMessage(), ex);
        }
        if (status == null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        GLOBAL_EXCEPTION_HANDLER_LOGGER.error("An error occurred: {}",
                ex.getMessage(), ex);

        ErrorResponse errorResponse =
                new ErrorResponse(ex.getMessage(), status);
        return new ResponseEntity<>(errorResponse, status);
    }
}
