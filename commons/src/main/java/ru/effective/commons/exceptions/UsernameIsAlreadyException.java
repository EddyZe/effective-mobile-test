package ru.effective.commons.exceptions;


public class UsernameIsAlreadyException extends RuntimeException{

    public UsernameIsAlreadyException(String msg) {
        super(msg);
    }
}
