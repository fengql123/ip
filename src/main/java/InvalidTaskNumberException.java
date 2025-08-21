public class InvalidTaskNumberException extends KrisException {
    public InvalidTaskNumberException(String taskNumber, int totalTasks) {
        super("Yo! Task number " + taskNumber + " doesn't exist! You have " + totalTasks + " tasks.");
    }
    
    public InvalidTaskNumberException(String invalidInput) {
        super("Yo! '" + invalidInput + "' ain't a valid number! Use a number like 1, 2, 3...");
    }
}