package ua.traning.rd.java.finalproject.servlet.exception;

public class DaoException extends RuntimeException {
    public DaoException(Exception ex) {
        super(ex);
    }

    public DaoException(String message) {
        super(message);
    }

    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }

    public DaoException(Throwable cause) {
        super(cause);
    }
}
