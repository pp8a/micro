package com.micro.restaurant_service.service;

import java.util.List;
import java.util.Optional;

import com.micro.restaurant_service.dto.OrderApprovalDTO;

public interface OrderApprovalService {
	/**
	 * Creates a new order approval.
	 * @param orderApprovalDTO the order approval DTO
	 * @return the created OrderApprovalDTO
	 */
	OrderApprovalDTO createOrderApproval(OrderApprovalDTO orderApprovalDTO);
	/**
	 * Order cancellation
	 * @param orderId the order ID
	 */
	public void cancelOrderApproval(Long orderId);
	/**
	 * Gets the order approval by ID.
	 * @param approvalId the order approval ID
	 * @return the OrderApprovalDTO
	 */
	Optional<OrderApprovalDTO> getOrderApprovalById(Long approvalId);
	/**
	 * Gets all orders approval.
	 * @return the list of order approval DTOs 
	 */
	List<OrderApprovalDTO> getAllOrderApproval();
	/**
	 * Updates the order approval parameters.
	 * @param approvalId the order approval ID
	 * @param orderApprovalDTO the order approval DTO
	 * @return the updated order approval DTO
	 */
	OrderApprovalDTO updateOrderApprovalDTO(Long approvalId, OrderApprovalDTO orderApprovalDTO);
	/**
	 * Deletes the order approval by ID.
	 * @param approvalId the order approval ID
	 */
	void deleteOrderApproval(Long approvalId);
}
