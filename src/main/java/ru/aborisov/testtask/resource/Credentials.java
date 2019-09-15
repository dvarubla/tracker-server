package ru.aborisov.testtask.resource;

import org.hibernate.validator.constraints.NotBlank;

public class Credentials {
    @NotBlank(message = "Логин не может быть пустым")
    private String login;
    @NotBlank(message = "Пароль не может быть пустым")
    private String password;

    public Credentials() {
    }

    public Credentials(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
