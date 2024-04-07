package sunposition.springdays.exception;

public final class HttpErrorExceptions {

    private HttpErrorExceptions() {
    }

    public static class CustomNotFoundException extends RuntimeException {
        public CustomNotFoundException(final String message) {
            super(message);
        }
    }

    public static class CustomBadRequestException extends RuntimeException {
        public CustomBadRequestException(final String message) {
            super(message);
        }
    }

    public static class CustomMethodNotAllowedException
            extends RuntimeException {
        public CustomMethodNotAllowedException(final String message) {
            super(message);
        }
    }

    public static class CustomServiceUnavailableException
            extends RuntimeException {
        public CustomServiceUnavailableException(final String message) {
            super(message);
        }
    }

    public static class CustomInternalServerErrorException
            extends RuntimeException {
        public CustomInternalServerErrorException(
                final String message, final Throwable cause) {
            super(message, cause);
        }
    }
}
