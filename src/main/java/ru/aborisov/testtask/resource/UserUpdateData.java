package ru.aborisov.testtask.resource;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.Optional;

public class UserUpdateData {
    @NotBlank(message = "Имя не может быть пустым")
    private String name;
    @NotNull(message = "id не может быть null")
    private Integer id;
    @NotNull(message = "id роли не может быть null")
    private Integer roleId;
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
