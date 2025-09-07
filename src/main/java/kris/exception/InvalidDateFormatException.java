package kris.exception;

/**
 * Exception thrown when a date string cannot be parsed into a valid date format.
 * Provides helpful suggestions for supported date formats.
 */
public class InvalidDateFormatException extends KrisException {
    /**
     * Constructs an InvalidDateFormatException for an unparseable date string.
     *
     * @param invalidDate The date string that could not be parsed.
     */
    public InvalidDateFormatException(String invalidDate) {
        super("Yo! I can't understand the date '" + invalidDate + "'! " +
                "Try these formats: yyyy-mm-dd (like 2019-12-02), d/m/yyyy (like 2/12/2019), " +
                "or add time with 4 digits (like 2/12/2019 1800)");
    }
}
