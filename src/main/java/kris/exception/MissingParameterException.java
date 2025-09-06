package kris.exception;

public class MissingParameterException extends KrisException {
    public MissingParameterException(String command, String parameter) {
        super("Yo! You gotta tell me which " + command + " " + parameter + "! Use '" + command + " [" + parameter + "]'.");
    }
    
    public MissingParameterException(String command, String parameter, String example) {
        super("Yo! Use '" + parameter + "' to specify the " + command + " " + example + "!");
    }
}