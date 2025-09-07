package kris.exception;

/**
 * Exception thrown when a task is created with an empty description.
 * Ensures all tasks have meaningful descriptions.
 */
public class EmptyDescriptionException extends KrisException {
    /**
     * Constructs an EmptyDescriptionException for a specific task type.
     *
     * @param taskType The type of task that has an empty description.
     */
    public EmptyDescriptionException(String taskType) {
        super("Yo! The description of a " + taskType + " cannot be empty, my friend!");
    }
}