package com.example.cineapp.models;

public class CambiarPassResponse {
    private String message;
    private String error;

    public String getMessage() { return message; }
    public String getError() { return error; }

    public boolean isSuccess() {
        return error == null || error.isEmpty();
    }
}
