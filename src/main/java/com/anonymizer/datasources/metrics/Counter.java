package com.anonymizer.datasources.metrics;

import java.util.concurrent.atomic.AtomicLong;

public class Counter implements Metric {

    private final AtomicLong count = new AtomicLong();

    public void increment() { this.add(1); }

    public void add(long n) { this.count.addAndGet(n); }

    @Override
    public Long getValue() {
        return count.get();
    }
}
