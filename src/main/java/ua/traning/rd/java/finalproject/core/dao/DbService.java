package ua.traning.rd.java.finalproject.core.dao;

import java.util.List;
import java.util.Optional;

public interface DbService<T> {
    Optional<List<T>> getAllBeans();

    Optional<T> getBeansById(int id);

    Optional<List<T>> getBeansBy(String columnName, Object value);

    Optional<List<T>> getBeansInRange(String columnName, List<Object> fields);

    Optional<List<T>> getBeansInRangeByRowNumber(int limit, int offset);

    int saveBean(T account);

    int updateBean(T account);

    int deleteBean(int id);
}
