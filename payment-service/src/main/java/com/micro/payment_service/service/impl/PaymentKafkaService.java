package com.micro.payment_service.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.micro.OrderAvro;
import com.micro.payment_service.dto.OrderAvroDTO;
import com.micro.payment_service.dto.PaymentDTO;
import com.micro.payment_service.exception.EntityNotFoundException;
import com.micro.payment_service.mapper.OrderAvroMapper;
import com.micro.payment_service.model.Payment;
import com.micro.payment_service.model.PaymentStatus;
import com.micro.payment_service.repository.PaymentRepository;
import com.micro.payment_service.service.PaymentService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PaymentKafkaService {
	private static final Logger LOGGER = LoggerFactory.getLogger(PaymentKafkaService.class);
	
	private KafkaTemplate<String, OrderAvro> kafkaTemplate;
	private PaymentRepository paymentRepository;
	private OrderAvroMapper orderAvroMapper;
	private PaymentService paymentService;
			
	@KafkaListener(topics = "payment-request-topic", groupId = "payment-service-group")
	public void listenPaymentRequest(OrderAvro orderAvro) {
		LOGGER.info("Received payment request for order: " + orderAvro.getId());
		OrderAvroDTO orderAvroDto = orderAvroMapper.orderAvroToOrderAvroDTO(orderAvro);		
		PaymentDTO paymentDTO = orderAvroMapper.orderAvroDTOToPaymentDTO(orderAvroDto);
		
		if(orderAvro.getOrderStatus().equals(PaymentStatus.CANCELLING.toString())) {			
			LOGGER.info("Cancelling payment for order: " + orderAvro.getId());
			Long orderId = paymentDTO.getOrderId();			
			paymentService.cancelPayment(orderId);  
			Payment orderPayment = paymentRepository.findByOrderId(orderId)
				.orElseThrow(() -> new EntityNotFoundException(
						"Payment not found for order ID: " + orderId));
			
            orderAvro.setOrderStatus(orderPayment.getPaymentStatus().toString());            
		} else if(paymentRepository.findByOrderId(paymentDTO.getOrderId()).isPresent()) {
			LOGGER.info("Payment already exists for order: " + orderAvro.getId());
			orderAvro.setOrderStatus(PaymentStatus.CANCELLED.toString());
		} else {
			paymentDTO = paymentService.createPayment(paymentDTO);
			orderAvro.setOrderStatus(paymentDTO.getPaymentStatus().toString());
		}
		
		sendPaymentResponse(orderAvro);
	}
	
	public void sendPaymentResponse(OrderAvro orderAvro) {
        kafkaTemplate.send("payment-response-topic", orderAvro);
    }

}
