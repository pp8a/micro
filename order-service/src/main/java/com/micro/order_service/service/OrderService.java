package com.micro.order_service.service;

import java.util.List;
import java.util.Optional;

import com.micro.order_service.dto.OrderDTO;

/**
 * Service interface for managing orders.
 */
public interface OrderService {	
	/**
     * Creates a new order.
     *
     * @param orderDTO the order DTO
     * @return the created order DTO
     */
	OrderDTO createOrder(OrderDTO orderDTO);
	/**
	 * Order cancellation
	 * @param orderId the order ID
	 */
	void cancelOrder(Long orderId);
	/**
     * Gets an order by ID.
     *
     * @param orderId the order ID
     * @return the order DTO
     */
	Optional<OrderDTO> getOrderById(Long orderId);
	/**
     * Gets all orders.
     *
     * @return the list of order DTOs
     */
	List<OrderDTO> getAllOrders();
	/**
     * Updates the order parameters.
     *
     * @param orderId the order ID
     * @param orderDTO the order DTO
     * @return the updated order DTO
     */
	OrderDTO updateOrder(Long orderId, OrderDTO orderDTO);
	/**
     * Deletes an order by ID.
     *
     * @param orderId the order ID
     */
	void deleteOrder(Long orderId);
}