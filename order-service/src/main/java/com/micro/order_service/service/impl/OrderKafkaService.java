package com.micro.order_service.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.micro.OrderAvro;
import com.micro.order_service.model.Order;
import com.micro.order_service.dto.OrderDTO;
import com.micro.order_service.exception.EntityNotFoundException;
import com.micro.order_service.mapper.OrderAvroMapper;
import com.micro.order_service.mapper.OrderMapper;
import com.micro.order_service.model.OrderStatus;
import com.micro.order_service.repository.OrderRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OrderKafkaService {	
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderKafkaService.class);
	
	private KafkaTemplate<String, OrderAvro> kafkaTemplate;
	private OrderRepository orderRepository;
	private OrderAvroMapper orderAvroMapper;
	private OrderMapper orderMapper;	

	public void sendPaymentRequest(OrderDTO orderDTO) {
		OrderAvro avroOrder = orderAvroMapper.entityToAvro(orderDTO);
		kafkaTemplate.send("payment-request-topic", avroOrder);
	}
	
	public void sendCancellationRequest(OrderDTO orderDTO) {
        OrderAvro avroOrder = orderAvroMapper.entityToAvro(orderDTO);
        kafkaTemplate.send("payment-request-topic", avroOrder);
    }
	
	@KafkaListener(topics = "payment-response-topic", groupId = "order-service-group")
	public void listenPaymentResponse(OrderAvro avroOrder) {
		LOGGER.info("Received payment response for order: " + avroOrder.getId());		
		Order order = orderRepository
				.findById(orderAvroMapper.toLongId(avroOrder.getId()))
				.orElseThrow(() -> new EntityNotFoundException("Order not found with id: " + avroOrder.getId()));
		order.setOrderStatus(OrderStatus.PAID);
        orderRepository.save(order);
        
        OrderDTO orderDTO = orderMapper.orderToOrderDTO(order);
        sendRestaurantApprovalRequest(orderDTO);
	}
	
	public void sendRestaurantApprovalRequest(OrderDTO orderDTO) {
		OrderAvro avroOrder = orderAvroMapper.entityToAvro(orderDTO);
		kafkaTemplate.send("restaurant-approval-request-topic", avroOrder);
	}
	
	@KafkaListener(topics = "restaurant-approval-response-topic", groupId = "order-service-group")
	public void listenRestaurantApprovalResponse(OrderAvro avroOrder) {
        LOGGER.info("Received restaurant approval response for order: " + avroOrder.getId());
        Order order = orderRepository
				.findById(orderAvroMapper.toLongId(avroOrder.getId()))
				.orElseThrow(() -> new EntityNotFoundException("Order not found with id: " + avroOrder.getId()));
        order.setOrderStatus(OrderStatus.APPROVED);
        orderRepository.save(order);
    }
}
