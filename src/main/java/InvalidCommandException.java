public class InvalidCommandException extends KrisException {
    public InvalidCommandException(String command) {
        super("Yo! I don't know what '" + command + "' means, my friend! Check out the help below.");
    }
}