package com.mohamedbamoh.foodie.domain.event.publisher;

import com.mohamedbamoh.foodie.domain.event.DomainEvent;

public interface DomainEventPublisher<T extends DomainEvent> {

    void publish(T domainEvent);
}
