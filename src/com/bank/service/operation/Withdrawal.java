package com.bank.model.operation;

import java.math.BigDecimal;

public class Withdrawal extends Operation {
    private final String destination;

    public Withdrawal(BigDecimal amount, String destination) {
        super(amount);
        this.destination = destination;
    }

    public String getDestination() { return destination; }

    @Override
    public void display() {
        System.out.println("Withdrawal | Amount: " + amount +
                " | Destination: " + destination +
                " | Date: " + date);
    }
}