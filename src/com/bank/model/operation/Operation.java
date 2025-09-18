package com.bank.model.operation;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public abstract class Operation {
    protected final UUID id;
    protected final LocalDateTime date;
    protected final BigDecimal amount;

    public Operation(BigDecimal amount) {
        this.id = UUID.randomUUID();
        this.date = LocalDateTime.now();
        this.amount = amount;
    }

    public UUID getId() { return id; }
    public LocalDateTime getDate() { return date; }
    public BigDecimal getAmount() { return amount; }

    public abstract void display();
}