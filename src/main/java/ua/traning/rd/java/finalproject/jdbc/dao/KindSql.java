package ua.traning.rd.java.finalproject.jdbc.dao;

public class KindSql implements AbstractSql {
    String SELECT_BY_ID = "SELECT id, kind_ru, kind_en FROM kind where id = ?";

    String INSERT = "INSERT INTO kind (kind_ru, kind_en) VALUES (?, ?)";

    String UPDATE = "UPDATE kind SET kind_ru = ?, kind_en = ? WHERE id = ?";

    String SELECT_ALL = "SELECT id, kind_ru, kind_en FROM kind";

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
