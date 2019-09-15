package ru.aborisov.testtask.resource;

import java.util.Optional;

public class UserUpdateData {
    private String name;
    private int id;
    private int roleId;
    private Optional<String> password = Optional.empty();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public UserUpdateData(String name, Integer id, int roleId, Optional<String> password) {
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
