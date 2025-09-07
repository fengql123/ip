package kris.exception;

/**
 * Base exception class for all exceptions in the Kris task management system.
 * All custom exceptions in the application extend from this class.
 */
public class KrisException extends Exception {
    /**
     * Constructs a KrisException with the specified error message.
     *
     * @param message The error message describing the exception.
     */
    public KrisException(String message) {
        super(message);
    }
}