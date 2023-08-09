# Foodie Project

This project, named "Foodie", is a demonstration of various concepts in software development, including Microservices with Spring Boot, Clean Architecture, Domain-Driven Design (DDD), SAGA, Outbox, Kafka, and Maven multi-module.

## Overview

Foodie is a system that aims to provide a platform for food lovers to explore, discover, and order food. It consists of multiple microservices, each responsible for a specific aspect of the overall system. The project follows the principles of  Hexagonal Clean Architecture and Domain-Driven Design to ensure a modular, scalable, and maintainable codebase.

## High Level Architecture
![HLA](foodie-architecture.png)
## Concepts Used

- Hexagonal Clean Architecture with Ports & Adapters.
- DDD (Domain-Driven Design)
- SAGA Pattern for distributed transaction and data consistency (process & rollback with compensating tx) 
- Outbox Pattern for pulling Outbox table with scheduler (Saga Status)
  - Ensure idempotency using outbox in each service.
  - Prevent concurrency issues with optimistic locks & DB constraints.
  - Updating Saga & Order status for each operation. 
- CQRS Pattern with Event Sourcing.
- Maven Multi-Module
- Kafka
- Kubernetes 



