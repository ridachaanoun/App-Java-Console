package com.bank.model;

import com.bank.exception.OverdraftExceededException;
import com.bank.model.operation.Withdrawal;

import java.math.BigDecimal;

public class CurrentAccount extends Account {
    private final BigDecimal overdraftLimit; // positive value (e.g. 500 => min balance -500)

    public CurrentAccount(BigDecimal overdraftLimit, BigDecimal initialBalance) {
        super(initialBalance);
        this.overdraftLimit = overdraftLimit;
    }

    public BigDecimal getOverdraftLimit() { return overdraftLimit; }

    @Override
    public void withdraw(BigDecimal amount, String destination) {
        validatePositive(amount);
        BigDecimal newBalance = balance.subtract(amount);
        if (newBalance.compareTo(overdraftLimit.negate()) < 0) {
            throw new OverdraftExceededException("Withdrawal exceeds overdraft limit");
        }
        balance = newBalance;
        addOperation(new Withdrawal(amount, destination));
    }

    @Override
    public BigDecimal calcInterest() {
        return BigDecimal.ZERO;
    }

    @Override
    public void displayDetails() {
        System.out.println("CurrentAccount: " + code +
                " | Balance: " + balance +
                " | Overdraft: " + overdraftLimit);
    }
}