package ua.traning.rd.java.finalproject.core.dao;

import ua.traning.rd.java.finalproject.core.model.Account;

import java.util.List;

public interface DbService<T> {
    List<T> getAllBeans();

    T getBeansById(int id);

    int saveBean(T account);

    int updateBean(T account);
}
