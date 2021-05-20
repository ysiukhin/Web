package com.epam.rd.java.finalproject.core.dao;

import com.epam.rd.java.finalproject.core.model.Project;

public abstract class ProjectDao extends AbstractDao<Project> {
    protected static final String SELECT_BY_ID = "SELECT role_ru, role_en FROM role WHERE id= ?";

    protected static final String INSERT = "INSERT INTO account (first_name, last_name, middle_name, email, " +
            "login, md5, status, role_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    protected static final String UPDATE = "UPDATE account SET first_name = ?, last_name = ?, middle_name = ?," +
            "email = ?, login = ?, md5 = ?, status = ?, role_id = ? WHERE id = ?";

    protected static final String SELECT_ALL = "SELECT id, role_ru, role_en FROM role";
}
