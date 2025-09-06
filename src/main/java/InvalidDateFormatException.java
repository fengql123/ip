public class InvalidDateFormatException extends KrisException {
    public InvalidDateFormatException(String invalidDate) {
        super("Yo! I can't understand the date '" + invalidDate + "'! " +
              "Try these formats: yyyy-mm-dd (like 2019-12-02), d/m/yyyy (like 2/12/2019), " +
              "or add time with 4 digits (like 2/12/2019 1800)");
    }
}