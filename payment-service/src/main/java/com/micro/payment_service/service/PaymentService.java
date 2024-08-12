package com.micro.payment_service.service;

import java.util.List;
import java.util.Optional;

import com.micro.payment_service.dto.PaymentDTO;

public interface PaymentService {
	/**
	 * Creates a new payment.
	 * @param paymentDTO the payment DTO
	 * @return the created payment DTO
	 */
	PaymentDTO createPayment(PaymentDTO paymentDTO);
	/**
	 * Order cancellation
	 * @param orderId the order ID
	 */
	public void cancelPayment(Long orderId);
	/**
	 * Gets a payment by id
	 * @param paymentId the payment ID
	 * @return the payment DTO
	 */
	Optional<PaymentDTO> getPaymentById(Long paymentId);
	/**
	 * Gets all payments.
	 * @return the list of the payments DTO
	 */
	List<PaymentDTO> getAllPayments();
	/**
	 * Updates the payment parameters
	 * @param paymentId the payment ID
	 * @param paymentDetails the payment DTO
	 * @return the updated payment DTO
	 */
	PaymentDTO updatePayment(Long paymentId, PaymentDTO paymentDetails);
	/**
	 * Deletes a payment by ID
	 * @param paymentId the payment ID
	 */
	void deletePayment(Long paymentId);
}
