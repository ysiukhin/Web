package ua.traning.rd.java.finalproject.jdbc.dao;

import ua.traning.rd.java.finalproject.jdbc.dao.AbstractSql;

public class ActivitySql implements AbstractSql {
    String SELECT_BY_ID =
            "SELECT id, activity_en, activity_ru, status, kind_id FROM activity WHERE id = ?";

    String INSERT =
            "INSERT INTO activity (activity_en, activity_ru, status, kind_id) VALUES (?, ?, ?, ?)";

    String UPDATE =
            "UPDATE activity SET activity_en = ?, activity_ru = ?, status = ?, kind_id = ?  WHERE id = ?";

    String SELECT_ALL = "SELECT id, activity_en, activity_ru, status, kind_id FROM activity";

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
