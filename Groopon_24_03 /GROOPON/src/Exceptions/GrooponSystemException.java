package Exceptions;

/**
 * Exception class for message mechanism
 */
public class GrooponSystemException extends Exception {

    /**
     * GrooponSystem exception
     * @param message we want to show
     */
    public GrooponSystemException(String message) {
        super(message);
    }

}
