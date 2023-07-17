package com.mohamedbamoh.foodie.saga;

import com.mohamedbamoh.foodie.common.domain.event.DomainEvent;

public interface SagaStep<T, S extends DomainEvent, U extends DomainEvent> {

    S process(T data);

    U rollback(T data);
}
