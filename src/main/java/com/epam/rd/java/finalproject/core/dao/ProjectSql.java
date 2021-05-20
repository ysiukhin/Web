package com.epam.rd.java.finalproject.core.dao;

public class ProjectSql implements AbstractSql {
    String SELECT_BY_ID =
            "SELECT id, project_name, project_desc, status FROM project WHERE id = ?;";

    String INSERT =
            "INSERT INTO project (project_name, project_desc, status) VALUES (?, ?, ?)";

    String UPDATE =
            "UPDATE project SET project_name = ?, project_desc = ?, status = ? WHERE id = ?";

    String SELECT_ALL = "SELECT id, project_name, project_desc, status FROM project";

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
