package com.bank.model.operation;

import java.math.BigDecimal;

public class Deposit extends Operation {
    private final String source;

    public Deposit(BigDecimal amount, String source) {
        super(amount);
        this.source = source;
    }

    public String getSource() { return source; }

    @Override
    public void display() {
        System.out.println("Deposit    | Amount: " + amount +
                " | Source: " + source +
                " | Date: " + date);
    }
}