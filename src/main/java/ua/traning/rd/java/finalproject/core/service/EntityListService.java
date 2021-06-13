package ua.traning.rd.java.finalproject.core.service;

import java.util.List;

public interface EntityListService<T> {
    int totalEntityQuantity();

    List<T> totalEntityQuantityBySql(String sqlQuery);

    List<T> getInRangeByRowNumber(int limit, int offset);

    List<T> getInRangeByRowNumber(int limit, int offset, String sqlQuery);

    T getById(int id);

    int insertEntity(T entity);

    int updateEntity(T entity);

    int updateEntity(String sqlQuery, Object value);

    int deleteEntity(int id);

    List<T> getByStoredProc(String storedProc, List<Object> values);
}
