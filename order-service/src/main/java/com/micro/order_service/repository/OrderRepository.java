package com.micro.order_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.micro.order_service.model.Order;

/**
 * Repository interface for Order entities.
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{

}
