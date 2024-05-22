package ru.effective.commons.exceptions;



public class PhoneNumberNotFoundException extends RuntimeException{
    public PhoneNumberNotFoundException(String msg) {
        super(msg);
    }
}
