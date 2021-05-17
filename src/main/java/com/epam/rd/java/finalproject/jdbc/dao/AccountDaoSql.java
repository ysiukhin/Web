package com.epam.rd.java.finalproject.jdbc.dao;

public interface AccountDaoSql {
    String selectById =
            "SELECT a.id `id`, first_name, last_name, middle_name, email, login, md5, status, role_ru, role_en " +
                    "FROM account a INNER JOIN role r ON a.role_id = r.id WHERE id = ?;";

    String insertNewAccount = "INSERT INTO account (first_name, last_name, middle_name, email, " +
            "login, md5, status, role_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";

    String updateAccount = "UPDATE account SET first_name = ?, last_name = ?, middle_name = ?," +
            "email = ?, login = ?, md5 = ?, status = ?, role_id = ? WHERE id = 3;";

    String selectAllAccounts = "SELECT a.id, first_name, last_name, middle_name, email, login, md5, status, " +
            "role_ru, role_en FROM account a INNER JOIN role r ON a.role_id = r.id;";

}
