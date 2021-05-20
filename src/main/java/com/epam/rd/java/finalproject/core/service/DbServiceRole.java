package com.epam.rd.java.finalproject.core.service;

import com.epam.rd.java.finalproject.core.model.Role;

import java.util.List;
import java.util.Optional;

public interface DbServiceRole {
    Optional<List<Role>> getAllRoles();

    Optional<Role> getRole(int id);

    int saveRole(Role role);

    int updateRole(Role role);
}
