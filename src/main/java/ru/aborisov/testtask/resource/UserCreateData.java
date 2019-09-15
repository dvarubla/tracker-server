package ru.aborisov.testtask.resource;

public class UserCreateData {
    private String login;
    private String name;
    private int roleId;
    private String password;

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

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public UserCreateData() {
    }

    public UserCreateData(String login, String name, int roleId, String password) {
        this.login = login;
        this.name = name;
        this.roleId = roleId;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
