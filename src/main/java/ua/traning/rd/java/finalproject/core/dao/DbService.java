package ua.traning.rd.java.finalproject.core.dao;

import java.util.List;
import java.util.Optional;

public interface DbService<T> {
    Optional<List<T>> getAllBeans();

    Optional<T> getBeansById(int id);

    Optional<List<T>> getBeansBy(String columnName, Object value);

    int saveBean(T account);

    int updateBean(T account);
}
