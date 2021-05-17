package com.epam.rd.java.finalproject.core.model;

import java.io.Serializable;

public class Role implements Serializable {
    private int id;
    private String roleRu;

    public int getId() {
        return id;
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

    private String roleEn;
}
