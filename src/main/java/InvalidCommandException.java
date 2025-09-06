public class InvalidCommandException extends KrisException {
    public InvalidCommandException(String command) {
        super("Yo! I don't know what '" + command + "' means, my friend! Check out the help below.");
    }
    
    @Override
    public String getMessage() {
        return super.getMessage() + "\n Try these commands:\n" +
               " - todo [description]\n" +
               " - deadline [description] /by [time]\n" +
               " - event [description] /from [start] /to [end]\n" +
               " - list, mark [number], unmark [number], delete [number], bye";
    }
}