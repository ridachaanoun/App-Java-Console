package com.bank.util;

import java.util.concurrent.atomic.AtomicInteger;

public final class CodeGenerator {
    private static final AtomicInteger SEQ = new AtomicInteger(0);

    private CodeGenerator() {}

    public static String nextAccountCode() {
        int n = SEQ.incrementAndGet();
        return String.format("CPT-%05d", n);
    }
}