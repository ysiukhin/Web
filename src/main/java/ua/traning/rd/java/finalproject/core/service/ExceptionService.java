package ua.traning.rd.java.finalproject.core.service;

public class ExceptionService extends RuntimeException {
    public ExceptionService(String message) {
        super(message);
    }

    public ExceptionService(String message, Throwable cause) {
        super(message, cause);
    }

    public ExceptionService(Throwable cause) {
        super(cause);
    }
}
