package ua.traning.rd.java.finalproject.jdbc.dao;

public class AccountActivitySql implements AbstractSql {
    public static final String SELECT_BY_ID =
            "SELECT id, status, account_id, activity_id FROM account_activity WHERE id = ?";

//    public static final String SELECT_BY_FI =
//            "SELECT id, status, account_id, activity_id FROM account_activity WHERE id = ?";

    public static final String INSERT = "INSERT INTO account_activity (status, account_id, activity_id) VALUES (?, ?, ?)";

    public static final String UPDATE = "UPDATE account_activity SET status = ?, account_id = ?, activity_id = ? WHERE id = ?";

    public static final String SELECT_ALL = "SELECT id, status, account_id, activity_id FROM account_activity";

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
