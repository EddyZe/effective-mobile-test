package ru.effective.commons.exceptions;

public class NotAuthTokenException extends RuntimeException{

    public NotAuthTokenException(String msg) {
        super(msg);
    }
}
