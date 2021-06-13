package ua.traning.rd.java.finalproject.core.dao;

import java.util.List;
import java.util.Optional;

public interface DbService<T> {

    Optional<T> getBeansById(int id);

    List<T> getBeansBy(String columnName, Object value);

    List<T> getBeansByQuery(String procName, List<Object> values);

    List<T> getBeansByCall(String procName, List<Object> values);

    List<T> getBeansInRangeByRowNumber(int limit, int offset);

    List<T> getBeansInRangeByRowNumber(int limit, int offset, String sqlQuery);

    int saveBean(T account);

    int updateBean(T account);

    int updateBean(String sqlQuery, List<Object> values);

    int beanQuantity();
}
