public class EmptyDescriptionException extends KrisException {
    public EmptyDescriptionException(String taskType) {
        super("Yo! The description of a " + taskType + " cannot be empty, my friend!");
    }
}