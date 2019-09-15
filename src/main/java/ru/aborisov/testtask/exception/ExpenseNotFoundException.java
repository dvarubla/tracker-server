package ru.aborisov.testtask.exception;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;

@JsonAutoDetect(fieldVisibility = NONE, getterVisibility = NONE, setterVisibility = NONE)

public class ExpenseNotFoundException extends Exception {
    public ExpenseNotFoundException() {
    }

    public ExpenseNotFoundException(int id) {
        super(String.valueOf(id));
    }

    public ExpenseNotFoundException(int id, Throwable cause) {
        super(String.valueOf(id), cause);
    }

    public ExpenseNotFoundException(Throwable cause) {
        super(cause);
    }

    @Override
    @JsonProperty
    public String getMessage() {
        return "Запись с id " + super.getMessage() + " не найдена";
    }
}
