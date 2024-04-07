package sunposition.springdays.exception;

import org.springframework.http.HttpStatus;

public class ErrorResponse {
    private final String message;
    private final HttpStatus status;

    public ErrorResponse(
            final String errorMessage, final HttpStatus errorStatus) {
        this.message = errorMessage;
        this.status = errorStatus;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
