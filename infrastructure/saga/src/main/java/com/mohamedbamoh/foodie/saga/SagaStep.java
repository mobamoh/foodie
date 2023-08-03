package com.mohamedbamoh.foodie.saga;

public interface SagaStep<T> {

    void process(T data);

    void rollback(T data);
}
