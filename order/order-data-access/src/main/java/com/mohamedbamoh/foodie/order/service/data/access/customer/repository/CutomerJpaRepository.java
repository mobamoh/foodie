package com.mohamedbamoh.foodie.order.service.data.access.customer.repository;

import com.mohamedbamoh.foodie.order.service.data.access.customer.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CutomerJpaRepository extends JpaRepository<CustomerEntity, UUID> {
}
