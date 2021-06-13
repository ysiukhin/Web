package ua.traning.rd.java.finalproject.core.service;

import ua.traning.rd.java.finalproject.core.model.Account;

public interface LoginService {
    Account checkAccount(String email, String password);
}
