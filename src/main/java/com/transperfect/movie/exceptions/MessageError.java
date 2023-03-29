package com.transperfect.movie.exceptions;

public enum MessageError {

    INTERNAL_SERVER_ERR("something wrong in the server !"),

    NOT_FOUND_MOVIE("No movies found for genre !");

    private final String message;

    MessageError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
