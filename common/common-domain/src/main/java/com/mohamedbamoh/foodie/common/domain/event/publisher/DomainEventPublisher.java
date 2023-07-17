package com.mohamedbamoh.foodie.common.domain.event.publisher;

import com.mohamedbamoh.foodie.common.domain.event.DomainEvent;

public interface DomainEventPublisher<T extends DomainEvent> {

    void publish(T domainEvent);
}
