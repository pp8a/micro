package com.micro.order_service.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import com.micro.order_service.dto.OrderDTO;
import com.micro.order_service.exception.EntityDeletionException;
import com.micro.order_service.exception.EntityNotFoundException;
import com.micro.order_service.mapper.OrderMapper;
import com.micro.order_service.model.Order;
import com.micro.order_service.model.OrderStatus;
import com.micro.order_service.repository.OrderRepository;
import com.micro.order_service.service.OrderService;

import lombok.AllArgsConstructor;

/**
 * Service implementation for managing orders.
 */
@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService{
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);
	
	private OrderRepository orderRepository;
	private OrderMapper orderMapper;
	private OrderKafkaService orderKafkaService;

	@Override
	public OrderDTO createOrder(OrderDTO orderDTO) {
		LOGGER.debug("Creating a new order: {}", orderDTO);
		Order order = orderMapper.orderDTOToOrder(orderDTO);
		order.setOrderStatus(OrderStatus.PENDING);
		order = orderRepository.save(order);
		LOGGER.info("Order created with ID: {}", order.getId());		
		orderDTO = orderMapper.orderToOrderDTO(order);
		
		orderKafkaService.sendPaymentRequest(orderDTO);
		return orderDTO;
	}
	
	@Override
	public void cancelOrder(Long orderId) {
		Order order = orderRepository.findById(orderId)
	            .orElseThrow(() -> new EntityNotFoundException("Order not found with id: " + orderId));
		
		order.setOrderStatus(OrderStatus.CANCELLING);
	    orderRepository.save(order);
	        
	    OrderDTO orderDTO = orderMapper.orderToOrderDTO(order);
	    orderKafkaService.sendCancellationRequest(orderDTO);		
	}
	
	@Override
	public Optional<OrderDTO> getOrderById(Long orderId) {
		LOGGER.debug("Fetching order with ID: {}", orderId);
		return orderRepository.findById(orderId).map(orderMapper::orderToOrderDTO);
	}

	@Override
	public List<OrderDTO> getAllOrders() {
		LOGGER.debug("Fetching all orders");
		return orderRepository.findAll().stream()
				.map(orderMapper::orderToOrderDTO).toList();
	}

	@Override
	public OrderDTO updateOrder(Long orderId, OrderDTO orderDTO) {
		LOGGER.debug("Updating order with ID: {}", orderId);		
		return orderRepository.findById(orderId).map(order -> {
			order.setCustomerName(orderDTO.getCustomerName());
			order.setOrderStatus(orderDTO.getOrderStatus());
			order.setTotalAmount(orderDTO.getTotalAmount());			
			Order updatedOrder = orderRepository.saveAndFlush(order);
			LOGGER.info("Order updated with ID: {}", updatedOrder.getId());
			return orderMapper.orderToOrderDTO(updatedOrder);
		}).orElseThrow(() -> new EntityNotFoundException("Order not found with id: " + orderId));
	}

	@Override
	public void deleteOrder(Long orderId) {
		LOGGER.debug("Deleting order with ID: {}", orderId);
		if(orderRepository.existsById(orderId)) {
			orderRepository.deleteById(orderId);
			LOGGER.info("Order deleted with ID: {}", orderId);
		} else {
			LOGGER.error("Order not found with ID: {}", orderId);
	        throw new EntityDeletionException ("Order not found with id: " + orderId);
		}		
	}

	
}
