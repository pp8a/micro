package com.micro.payment_service.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


import com.micro.payment_service.dto.PaymentDTO;
import com.micro.payment_service.exception.EntityDeletionException;
import com.micro.payment_service.exception.EntityNotFoundException;
import com.micro.payment_service.mapper.PaymentMapper;
import com.micro.payment_service.model.Payment;
import com.micro.payment_service.model.PaymentStatus;
import com.micro.payment_service.repository.PaymentRepository;
import com.micro.payment_service.service.PaymentService;

import lombok.AllArgsConstructor;

/**
 * Service implementation for managing payments.
 */
@Service
@AllArgsConstructor
public class PaymentServiceImpl implements PaymentService {
	private static final Logger LOGGER = LoggerFactory.getLogger(PaymentServiceImpl.class);
	
	private PaymentRepository paymentRepository;
	private PaymentMapper paymentMapper;
	
	@Override
	public PaymentDTO createPayment(PaymentDTO paymentDTO) {
		LOGGER.debug("Creating a new payment: {}", paymentDTO);
		Payment payment = paymentMapper.paymentDTOToPayment(paymentDTO);
		payment.setPaymentStatus(PaymentStatus.PAID);
		payment = paymentRepository.save(payment);
		LOGGER.info("Payment created with ID: {}", payment.getId());		
		
		return paymentMapper.paymentToPaymentDTO(payment);
	}
	
	@Override
    public void cancelPayment(Long orderId) {
        paymentRepository.findByOrderId(orderId).ifPresent(payment -> {
            payment.setPaymentStatus(PaymentStatus.CANCELLED);
            paymentRepository.save(payment);
            LOGGER.info("Payment for order ID: {} has been cancelled", orderId);
        });
    }

	@Override
	public Optional<PaymentDTO> getPaymentById(Long paymentId) {
		LOGGER.debug("Fetching payment with ID: {}", paymentId);
		return paymentRepository.findById(paymentId)
				.map(paymentMapper::paymentToPaymentDTO);
	}

	@Override
	public List<PaymentDTO> getAllPayments() {
		LOGGER.debug("Fetching all payments");
		return paymentRepository.findAll().stream()
				.map(paymentMapper::paymentToPaymentDTO).toList();
	}

	@Override
	public PaymentDTO updatePayment(Long paymentId, PaymentDTO paymentDetails) {
		LOGGER.debug("Updating payment with ID: {}", paymentId);
		return paymentRepository.findById(paymentId).map(payment -> {
			payment.setPaymentStatus(paymentDetails.getPaymentStatus());			
			Payment updatedPayment = paymentRepository.save(payment);
			LOGGER.info("Payment updated with ID: {}", updatedPayment.getId());
			return paymentMapper.paymentToPaymentDTO(updatedPayment);
		}).orElseThrow(() -> new EntityNotFoundException("Order not found with id: " + paymentId));
	}

	@Override
	public void deletePayment(Long paymentId) {
		LOGGER.debug("Deleting payment with ID: {}", paymentId);
		if(paymentRepository.existsById(paymentId)) {
			paymentRepository.deleteById(paymentId);
			LOGGER.info("Payment deleted with ID: {}", paymentId);
		} else {
			LOGGER.error("Payment not found with ID: {}", paymentId);
	        throw new EntityDeletionException("Payment not found with id: " + paymentId);			
		}
	}

}
