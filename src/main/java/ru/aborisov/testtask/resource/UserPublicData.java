package ru.aborisov.testtask.resource;

import java.util.Objects;

public class UserPublicData {
    private String login;
    private String name;
    private int id;
    private RoleNameId role;

    public RoleNameId getRole() {
        return role;
    }

    public void setRole(RoleNameId role) {
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserPublicData() {
    }

    public UserPublicData(String login, String name, int id, RoleNameId role) {
        this.login = login;
        this.name = name;
        this.id = id;
        this.role = role;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPublicData that = (UserPublicData) o;
        return id == that.id &&
                Objects.equals(login, that.login) &&
                Objects.equals(name, that.name) &&
                Objects.equals(role, that.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, name, id, role);
    }
}
