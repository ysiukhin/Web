package com.epam.rd.java.finalproject.core.model;

import com.epam.rd.java.finalproject.core.annotation.TableField;

import java.io.Serializable;

public class Role implements Serializable {
    @TableField(value = "id", select = 1, insertPosition = 0, updatePosition = 3)
    private int id;
    @TableField(value = "role_ru", insertPosition = 1, updatePosition = 1)
    private String roleRu;
    @TableField(value = "role_en", insertPosition = 2, updatePosition = 2)
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
