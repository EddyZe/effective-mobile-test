package ru.effective.commons.exceptions;

public class MoneyTransferException extends RuntimeException{
    public MoneyTransferException(String msg) {
        super(msg);
    }
}
