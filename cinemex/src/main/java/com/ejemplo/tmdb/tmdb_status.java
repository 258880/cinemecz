package com.ejemplo.tmdb;

public class tmdb_status extends Exception {
    private final int statusCode;

    public tmdb_status(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
