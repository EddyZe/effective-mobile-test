package ru.effective.commons.exceptions;

public class EmailAddressNotFoundException extends RuntimeException{

    public EmailAddressNotFoundException(String msg) {
        super(msg);
    }
}
