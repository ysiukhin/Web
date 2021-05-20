package com.epam.rd.java.finalproject.core.dao;


public class AccountSql implements AbstractSql {
    public static final String SELECT_BY_ID =
            "SELECT a.id `id`, first_name, last_name, middle_name, email, login, md5, status, role_ru, role_en " +
                    "FROM account a INNER JOIN role r ON a.role_id = r.id WHERE id = ?";

    public static final String INSERT = "INSERT INTO account (first_name, last_name, middle_name, email, " +
            "login, md5, status, role_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    public static final String UPDATE = "UPDATE account SET first_name = ?, last_name = ?, middle_name = ?," +
            "email = ?, login = ?, md5 = ?, status = ?, role_id = ? WHERE id = ?";

    public static final String SELECT_ALL = "SELECT id, first_name, last_name, middle_name, email, " +
            "login, md5, status, role_id FROM account";

    public static final String SELECT_ALL_WITH_ROLE_ID_SUBSTITUTED = "SELECT a.id, first_name, last_name, " +
            "middle_name, email, login, md5, status, role_ru, role_en " +
            "FROM account a INNER JOIN role r ON a.role_id = r.id";

    @Override
    public String selectById() {
        return SELECT_BY_ID;
    }

    @Override
    public String insert() {
        return INSERT;
    }

    @Override
    public String update() {
        return UPDATE;
    }

    @Override
    public String selectAll() {
        return SELECT_ALL;
    }
}
