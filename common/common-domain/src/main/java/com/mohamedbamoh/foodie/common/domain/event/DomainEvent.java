package com.mohamedbamoh.foodie.common.domain.event;

public interface DomainEvent<T> {

    void fire();
}
