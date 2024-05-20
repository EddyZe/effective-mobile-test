package ru.effective.commons.exceptions;

public class UserInvalidException extends RuntimeException{

    public UserInvalidException(String msg) {
        super(msg);
    }
}
