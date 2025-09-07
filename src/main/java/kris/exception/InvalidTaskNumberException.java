package kris.exception;

/**
 * Exception thrown when an invalid task number is provided.
 * Handles both out-of-range task numbers and non-numeric input.
 */
public class InvalidTaskNumberException extends KrisException {
    /**
     * Constructs an InvalidTaskNumberException for an out-of-range task number.
     *
     * @param taskNumber The invalid task number that was provided.
     * @param totalTasks The total number of tasks available.
     */
    public InvalidTaskNumberException(String taskNumber, int totalTasks) {
        super("Yo! Task number " + taskNumber + " doesn't exist! You have " + totalTasks + " tasks.");
    }
    
    /**
     * Constructs an InvalidTaskNumberException for non-numeric input.
     *
     * @param invalidInput The invalid input that could not be parsed as a number.
     */
    public InvalidTaskNumberException(String invalidInput) {
        super("Yo! '" + invalidInput + "' ain't a valid number! Use a number like 1, 2, 3...");
    }
}