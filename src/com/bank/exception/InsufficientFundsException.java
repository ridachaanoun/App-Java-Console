package com.bank.exception;

public class InsufficientFundsException extends BankException {
    public InsufficientFundsException(String msg) {
        super(msg);
    }
}