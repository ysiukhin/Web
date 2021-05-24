package ua.traning.rd.java.finalproject.jdbc.dao;

public class AccountSql implements AbstractSql {
    public static final String SELECT_BY_ID =
            "SELECT id, first_name, last_name, middle_name, email, md5, status FROM account WHERE id = ?";

    public static final String INSERT = "INSERT INTO account (first_name, last_name, middle_name, " +
            "email, md5, status) VALUES (?, ?, ?, ?, ?, ?)";

    public static final String UPDATE = "UPDATE account SET first_name = ?, last_name = ?, middle_name = ?, " +
            "email = ?, md5 = ?, status = ? WHERE id = ?";

    public static final String SELECT_ALL = "SELECT id, first_name, last_name, middle_name, email, " +
            "md5, status FROM account";

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
