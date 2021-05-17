package com.epam.rd.java.finalproject.core.model;

public class AccountBuilder {
    private int id = 0;
    private String firstName = "";
    private String lastName = "";
    private String middleName = "";
    private String email = "";
    private String login = "";
    private String md5 = "";
    private boolean status = false;
    private int roleId = 1;
    private Role role = new Role();

    public AccountBuilder addId(int id) {
        this.id = id;
        return this;
    }

    public AccountBuilder addFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public AccountBuilder addLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public AccountBuilder addMiddleName(String middleName) {
        this.middleName = middleName;
        return this;
    }

    public AccountBuilder addEmail(String email) {
        this.email = email;
        return this;
    }

    public AccountBuilder addLogin(String login) {
        this.login = login;
        return this;
    }

    public AccountBuilder addMd5(String md5) {
        this.md5 = md5;
        return this;
    }

    public AccountBuilder addStatus(Boolean status) {
        this.status = status;
        return this;
    }

    public AccountBuilder addRoleId(int roleId) {
        this.roleId = roleId;
        return this;
    }

    public AccountBuilder addRole(Role role) {
        this.role = role;
        return this;
    }

    public Account build() {
        Account newAccount = new Account();
        newAccount.setId(id);
        newAccount.setFirstName(firstName);
        newAccount.setLastName(lastName);
        newAccount.setMiddleName(middleName);
        newAccount.setEmail(email);
        newAccount.setLogin(login);
        newAccount.setMd5(md5);
        newAccount.setStatus(status);
        newAccount.setRoleId(roleId);
        newAccount.setRole(role);
        return newAccount;
    }
}
