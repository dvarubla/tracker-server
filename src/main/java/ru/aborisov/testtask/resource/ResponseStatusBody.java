package ru.aborisov.testtask.resource;

public class ResponseStatusBody {
    private String message;

    public ResponseStatusBody(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
