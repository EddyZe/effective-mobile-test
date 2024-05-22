package ru.effective.commons.exceptions;

public class EmailInvalidException extends RuntimeException{

    public EmailInvalidException(String msg) {
        super(msg);
    }
}
