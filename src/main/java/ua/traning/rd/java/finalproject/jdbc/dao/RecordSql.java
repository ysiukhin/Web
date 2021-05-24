package ua.traning.rd.java.finalproject.jdbc.dao;

public class RecordSql implements AbstractSql {
    String SELECT_BY_ID = "SELECT id, start, end, account_activity_id FROM record WHERE id = ?";

    String INSERT = "INSERT INTO record (start, end, account_activity_id) VALUES (?, ?, ?)";

    String UPDATE = "UPDATE activity SET start = ?, end = ?, account_activity_id = ? WHERE id = ?";

    String SELECT_ALL = "SELECT id, start, end, account_activity_id FROM record";

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
