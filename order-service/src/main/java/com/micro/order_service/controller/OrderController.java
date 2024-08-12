package com.micro.order_service.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.micro.order_service.dto.OrderDTO;
import com.micro.order_service.exception.EntityNotFoundException;
import com.micro.order_service.service.OrderService;

/**
 * REST controller for managing orders.
 */
@RestController
@RequestMapping("/orders")
public class OrderController {
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);
	private OrderService orderService;

	public OrderController(OrderService orderService) {
		super();
		this.orderService = orderService;
	}
	
	@PostMapping
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO orderDTO) {
		LOGGER.debug("Received request to create order: {}", orderDTO);
		OrderDTO createdOrder = orderService.createOrder(orderDTO);
		LOGGER.info("Order created with ID: {}", createdOrder.getId());
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
    	LOGGER.debug("Received request to fetch all orders");
    	List<OrderDTO> orders = orderService.getAllOrders();
    	LOGGER.info("Fetched all orders");
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long orderId) {    	
    	LOGGER.debug("Received request to fetch order with ID: {}", orderId);
    	OrderDTO orderDTO = orderService.getOrderById(orderId)		
				.orElseThrow(() -> {
					LOGGER.error("Order not found with ID: {}", orderId);
					return new EntityNotFoundException("Order not found with id: " + orderId);
				});
		LOGGER.info("Fetched order with ID: {}", orderId);
        return ResponseEntity.ok(orderDTO);
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<OrderDTO> updateOrder(@PathVariable Long orderId, @RequestBody OrderDTO orderDTO) {
    	LOGGER.debug("Received request to update order with ID: {}", orderId);
    	OrderDTO updatedOrder = orderService.updateOrder(orderId, orderDTO);
    	LOGGER.info("Order updated with ID: {}", updatedOrder.getId());
        return ResponseEntity.ok(updatedOrder);
    }
    
    @DeleteMapping("/{orderId}")
	public ResponseEntity<Void> deleteService(@PathVariable Long orderId){
    	LOGGER.debug("Received request to delete order with ID: {}", orderId);
		orderService.deleteOrder(orderId);
		LOGGER.info("Order deleted with ID: {}", orderId);
		return ResponseEntity.noContent().build();    	
    }
}
