package ua.traning.rd.java.finalproject.core.dao;

public class DbServiceException extends RuntimeException {
    public DbServiceException(Exception e) {
        super(e);
    }
}
