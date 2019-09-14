package ru.aborisov.testtask.resource;

import java.util.Optional;

public class UserUpdateData {
    private String login;
    private String name;
    private Optional<Integer> id = Optional.empty();
    private int roleId;
    private Optional<String> password = Optional.empty();

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Optional<Integer> getId() {
        return id;
    }

    public void setId(Optional<Integer> id) {
        this.id = id;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public UserUpdateData() {
    }

    public UserUpdateData(String login, String name, Optional<Integer> id, int roleId, Optional<String> password) {
        this.login = login;
        this.name = name;
        this.id = id;
        this.roleId = roleId;
        this.password = password;
    }

    public Optional<String> getPassword() {
        return password;
    }

    public void setPassword(Optional<String> password) {
        this.password = password;
    }
}
