package com.epam.rd.java.finalproject.core.model;

import java.io.Serializable;

public class Role implements Serializable {
    private int id;
    private String roleRu;
    private String roleEn;

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Role{" +
                " roleRu='" + roleRu + '\'' +
                ", roleEn='" + roleEn + '\'' +
                '}';
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoleRu() {
        return roleRu;
    }

    public void setRoleRu(String roleRu) {
        this.roleRu = roleRu;
    }

    public String getRoleEn() {
        return roleEn;
    }

    public void setRoleEn(String roleEn) {
        this.roleEn = roleEn;
    }

}
