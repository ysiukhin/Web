package ua.traning.rd.java.finalproject.servlet.exception;

public class LoginPasswordException extends RuntimeException {
    public LoginPasswordException(String message) {
        super(message);
    }

    public LoginPasswordException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginPasswordException(Throwable cause) {
        super(cause);
    }
}
