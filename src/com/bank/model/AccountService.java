package com.bank.service;

import com.bank.exception.AccountNotFoundException;
import com.bank.model.Account;
import com.bank.model.CurrentAccount;
import com.bank.model.SavingsAccount;

import java.math.BigDecimal;
import java.util.*;

public class AccountService {

    private final Map<String, Account> accounts = new LinkedHashMap<>();

    public CurrentAccount createCurrent(BigDecimal overdraft, BigDecimal initial) {
        CurrentAccount c = new CurrentAccount(overdraft, initial);
        accounts.put(c.getCode(), c);
        return c;
    }

    public SavingsAccount createSavings(BigDecimal initial, BigDecimal rate) {
        SavingsAccount s = new SavingsAccount(initial, rate);
        accounts.put(s.getCode(), s);
        return s;
    }

    public Account find(String code) {
        Account a = accounts.get(code);
        if (a == null) throw new AccountNotFoundException(code);
        return a;
    }

    public Collection<Account> all() {
        return accounts.values();
    }

    public void transfer(String fromCode, String toCode, BigDecimal amount) {
        if (fromCode.equals(toCode)) {
            throw new IllegalArgumentException("Source and destination must differ");
        }
        Account from = find(fromCode);
        Account to = find(toCode);
        from.withdraw(amount, "Transfer to " + toCode);
        to.deposit(amount, "Transfer from " + fromCode);
    }
}