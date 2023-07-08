package com.mohamedbamoh.foodie.order.service.data.access.restaurant.repository;

import com.mohamedbamoh.foodie.order.service.data.access.restaurant.entity.RestaurantEntity;
import com.mohamedbamoh.foodie.order.service.data.access.restaurant.entity.RestaurantEntityId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RestaurantJpaRepository extends JpaRepository<RestaurantEntity, RestaurantEntityId> {

    Optional<List<RestaurantEntity>> findByIdAndProductIdIn(UUID id, List<UUID> productIds);
}
