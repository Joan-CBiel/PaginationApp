package exceptions;

/**
 * Exception that should raise if the document isn't a one line document.
 */
public class NotOneLineTextException extends Exception {
    public NotOneLineTextException(String message) {
        super(message);
    }
}