package com.micro.restaurant_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.micro.restaurant_service.model.OrderApproval;

public interface OrderApprovalRepository extends JpaRepository<OrderApproval, Long> {
	Optional<OrderApproval> findByOrderId(Long orderId);
}
