package com.bank.model;

import com.bank.exception.InsufficientFundsException;
import com.bank.model.operation.Withdrawal;

import java.math.BigDecimal;

public class SavingsAccount extends Account {
    private final BigDecimal interestRate; // e.g., 0.05 for 5%

    public SavingsAccount(BigDecimal initialBalance, BigDecimal interestRate) {
        super(initialBalance);
        this.interestRate = interestRate;
    }

    public BigDecimal getInterestRate() { return interestRate; }

    @Override
    public void withdraw(BigDecimal amount, String destination) {
        validatePositive(amount);
        if (balance.compareTo(amount) < 0) {
            throw new InsufficientFundsException("Insufficient funds");
        }
        balance = balance.subtract(amount);
        addOperation(new Withdrawal(amount, destination));
    }

    @Override
    public BigDecimal calcInterest() {
        return balance.multiply(interestRate);
    }

    @Override
    public void displayDetails() {
        System.out.println("SavingsAccount: " + code +
                " | Balance: " + balance +
                " | Rate: " + interestRate);
    }
}