package ru.aborisov.testtask.resource;

import javax.validation.constraints.NotNull;

public class Id {
    @NotNull(message = "id не может быть null")
    private Integer id;

    public Id() {

    }

    public Id(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
