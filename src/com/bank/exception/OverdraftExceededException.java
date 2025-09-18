package com.bank.exception;

public class OverdraftExceededException extends BankException {
    public OverdraftExceededException(String msg) {
        super(msg);
    }
}