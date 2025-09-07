package kris.exception;

/**
 * Exception thrown when an invalid or unrecognized command is entered.
 * Provides helpful error messages with available command options.
 */
public class InvalidCommandException extends KrisException {
    /**
     * Constructs an InvalidCommandException for an unrecognized command.
     *
     * @param command The invalid command that was entered.
     */
    public InvalidCommandException(String command) {
        super("Yo! I don't know what '" + command + "' means, my friend! Check out the help below.");
    }

    @Override
    public String getMessage() {
        return super.getMessage() + "\n Try these commands:\n" +
                " - todo [description]\n" +
                " - deadline [description] /by [time]\n" +
                " - event [description] /from [start] /to [end]\n" +
                " - list, mark [number], unmark [number], delete [number], find [keyword], bye";
    }
}
