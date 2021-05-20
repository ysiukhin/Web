package com.epam.rd.java.finalproject.core.dao;

public class RoleSql implements AbstractSql {
    String SELECT_BY_ID = "SELECT role_ru, role_en FROM role WHERE id= ?";

    String INSERT = "INSERT INTO role (role_ru, role_en) VALUES (?, ?)";

    String UPDATE = "UPDATE role SET role_ru = ?, role_en = ? WHERE id = ?";

    String SELECT_ALL = "SELECT id, role_ru, role_en FROM role";

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
