package com.micro.payment_service.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.micro.payment_service.dto.PaymentDTO;
import com.micro.payment_service.exception.EntityNotFoundException;
import com.micro.payment_service.service.PaymentService;

import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@AllArgsConstructor
@RequestMapping("/payments")
public class PaymentController {
	private static final Logger LOGGER = LoggerFactory.getLogger(PaymentController.class);
	
	private PaymentService paymentService;	
	
	@PostMapping
	public ResponseEntity<PaymentDTO> createPayment(@RequestBody PaymentDTO paymentDTO) {
		LOGGER.debug("Received request to create payment: {}", paymentDTO);
		PaymentDTO createdPayment = paymentService.createPayment(paymentDTO);
		LOGGER.info("Payment created with ID: {}", createdPayment.getId());
		return new ResponseEntity<>(createdPayment, HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<List<PaymentDTO>> getAllPayment() {
		LOGGER.debug("Received request to fetch all payments");
		List<PaymentDTO> payments = paymentService.getAllPayments();
		LOGGER.info("Fetched all payments");
		return ResponseEntity.ok(payments);
	}
	
	@GetMapping("/{paymentId}")
	public ResponseEntity<PaymentDTO> getPaymentById(@PathVariable Long paymentId) {
		LOGGER.debug("Received request to fetch the payment with ID: {}", paymentId);
		PaymentDTO payment = paymentService.getPaymentById(paymentId).orElseThrow(() -> {
			LOGGER.error("Payment not found with ID: {}", paymentId);
			return new EntityNotFoundException("Payment not found with id: " + paymentId);
		});
		LOGGER.info("Fetched payment with ID: {}", paymentId);
		return ResponseEntity.ok(payment);
	}
	
	@PutMapping("/{paymentId}")
	public ResponseEntity<PaymentDTO> updatePayment(@PathVariable Long paymentId, @RequestBody PaymentDTO paymentDTO) {
		LOGGER.debug("Received request to update payment with ID: {}", paymentId);
		PaymentDTO updatedPayment = paymentService.updatePayment(paymentId, paymentDTO);
		LOGGER.info("Payment updated with ID: {}", updatedPayment.getId());
		return ResponseEntity.ok(updatedPayment);
	}
	
	@DeleteMapping("/{paymentId}")
	public ResponseEntity<Void> deletePayment(@PathVariable Long paymentId){
		LOGGER.debug("Received request to delete order with ID: {}", paymentId);
		paymentService.deletePayment(paymentId);
		LOGGER.info("Order deleted with ID: {}", paymentId);
		return ResponseEntity.noContent().build();
	}

}
