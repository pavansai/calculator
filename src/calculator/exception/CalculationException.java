package calculator.exception;

/**
 * Exception thrown when a calculation error occurs.
 */
public class CalculationException extends RuntimeException {
    /**
     * Creates a new calculation exception with the specified message.
     *
     * @param message the error message
     */
    public CalculationException(String message) {
        super(message);
    }

    /**
     * Creates a new calculation exception with the specified message and cause.
     *
     * @param message the error message
     * @param cause the cause of the exception
     */
    public CalculationException(String message, Throwable cause) {
        super(message, cause);
    }
}