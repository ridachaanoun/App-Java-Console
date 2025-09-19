package com.bank.model;

import com.bank.model.operation.Operation;
import com.bank.model.operation.Deposit;
import com.bank.exception.InvalidAmountException;
import com.bank.util.CodeGenerator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public abstract class Account {
    protected final String code;
    protected BigDecimal balance;
    protected final List<Operation> operations;

    public Account() {
        this(CodeGenerator.nextAccountCode(), BigDecimal.ZERO);
    }

    public Account(BigDecimal initial) {
        this(CodeGenerator.nextAccountCode(), initial);
    }

    public Account(String code, BigDecimal initial) {
        this.code = code;
        this.balance = initial;
        this.operations = new ArrayList<>();
    }

    public String getCode() { return code; }
    public BigDecimal getBalance() { return balance; }
    public List<Operation> getOperations() { return operations; }

    protected void validatePositive(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0)
            throw new InvalidAmountException("Amount must be > 0");
    }

    public void addOperation(Operation op) {
        operations.add(op);
    }

    public void deposit(BigDecimal amount, String source) {
        validatePositive(amount);
        balance = balance.add(amount);
        addOperation(new Deposit(amount, source));
    }

    public abstract void withdraw(BigDecimal amount, String destination);
    public abstract BigDecimal calcInterest(); // For savings returns interest; others 0
    public abstract void displayDetails();
}