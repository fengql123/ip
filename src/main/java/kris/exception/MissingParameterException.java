package kris.exception;

/**
 * Exception thrown when required parameters are missing from a command.
 * Provides helpful error messages indicating what parameters are needed.
 */
public class MissingParameterException extends KrisException {
    /**
     * Constructs a MissingParameterException for a command missing a parameter.
     *
     * @param command The command that is missing a parameter.
     * @param parameter The parameter that is missing.
     */
    public MissingParameterException(String command, String parameter) {
        super("Yo! You gotta tell me which " + command + " " + parameter + "! Use '" + command + " [" + parameter + "]'.");
    }
    
    /**
     * Constructs a MissingParameterException with an example usage.
     *
     * @param command The command that is missing a parameter.
     * @param parameter The parameter that is missing.
     * @param example An example of how to use the parameter.
     */
    public MissingParameterException(String command, String parameter, String example) {
        super("Yo! Use '" + parameter + "' to specify the " + command + " " + example + "!");
    }
}