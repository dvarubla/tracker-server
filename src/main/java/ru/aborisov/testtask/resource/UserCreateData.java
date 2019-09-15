package ru.aborisov.testtask.resource;


import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

public class UserCreateData {
    @NotBlank(message = "Логин не может быть пустым")
    private String login;
    @NotBlank(message = "Имя не может быть пустым")
    private String name;
    @NotNull(message = "ID роли должен быть заполнен")
    private Integer roleId;
    @NotBlank(message = "Пароль не может быть пустым")
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
