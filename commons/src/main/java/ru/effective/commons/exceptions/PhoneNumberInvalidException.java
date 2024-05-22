package ru.effective.commons.exceptions;

public class PhoneNumberInvalidException extends RuntimeException{

    public PhoneNumberInvalidException(String msg) {
        super(msg);
    }
}
