package com.mohamedbamoh.foodie.outbox;

public interface OutboxScheduler {
    void processOutboxMessage();
}
