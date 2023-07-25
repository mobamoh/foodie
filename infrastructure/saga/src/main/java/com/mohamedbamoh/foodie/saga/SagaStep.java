package com.mohamedbamoh.foodie.saga;

public interface SagaStep<T> {

    void process(T data);

    void rollback(T data);
}

//public interface SagaStep<T, S extends DomainEvent, U extends DomainEvent> {
//
//    S process(T data);
//
//    U rollback(T data);
//}