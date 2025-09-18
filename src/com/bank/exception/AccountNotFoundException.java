package com.bank.exception;

public class AccountNotFoundException extends BankException {
    public AccountNotFoundException(String code) {
        super("Account not found: " + code);
    }
}