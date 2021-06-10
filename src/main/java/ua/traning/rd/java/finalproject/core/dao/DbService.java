package ua.traning.rd.java.finalproject.core.dao;

import java.util.List;
import java.util.Optional;

public interface DbService<T> {
    Optional<List<T>> getAllBeans();

    Optional<T> getBeansById(int id);

    Optional<List<T>> getBeansBy(String columnName, Object value);

    Optional<List<T>> getBeansBy(List<String> columnNames, List<Object> values);

    Optional<List<T>> getBeansByQuery(String procName, List<Object> values);

    Optional<List<T>> getBeansByCall(String procName, List<Object> values);

    Optional<List<T>> getBeansByCall(String procName, Object value);

    Optional<List<T>> getBeansInRangeByRowNumber(int limit, int offset);

    Optional<List<T>> getBeansInRangeByRowNumber(int limit, int offset, String sqlQuery);

    Optional<List<T>> getBeansFromList(String columnName, List<Object> fields);

    int saveBean(T account);

    int updateBean(T account);

    int updateBean(String sqlQuery, List<Object> values);

    int beanQuantity();
}
