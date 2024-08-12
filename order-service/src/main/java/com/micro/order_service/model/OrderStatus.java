package com.micro.order_service.model;

public enum OrderStatus {
	PENDING,    // Заказ создан, но еще не оплачен
    PAID,       // Заказ оплачен
    APPROVED,   // Заказ одобрен рестораном
    CANCELLED,  // Заказ отменен
    CANCELLING  // Процесс отмены заказа
}
