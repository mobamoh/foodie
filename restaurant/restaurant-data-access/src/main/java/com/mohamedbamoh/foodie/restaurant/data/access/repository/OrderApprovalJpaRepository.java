package com.mohamedbamoh.foodie.restaurant.data.access.repository;

import com.mohamedbamoh.foodie.restaurant.data.access.entity.OrderApprovalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderApprovalJpaRepository extends JpaRepository<OrderApprovalEntity, UUID> {
}
