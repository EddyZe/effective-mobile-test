package ru.effective.commons.exceptions;

public class PhoneNumberIsAlreadyException extends RuntimeException{
    public PhoneNumberIsAlreadyException(String msg) {
        super(msg);
    }
}
