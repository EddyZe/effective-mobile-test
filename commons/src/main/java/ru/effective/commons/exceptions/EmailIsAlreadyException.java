package ru.effective.commons.exceptions;

public class EmailIsAlreadyException extends RuntimeException{
    public EmailIsAlreadyException (String msg) {
        super(msg);
    }
}
