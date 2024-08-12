INSERT INTO payments (order_id, payment_status, amount) 
VALUES (1, 'CANCELLED', 100.00) 
ON CONFLICT DO NOTHING;

INSERT INTO payments (order_id, payment_status, amount) 
VALUES (2, 'PAID', 74.90) 
ON CONFLICT DO NOTHING;

INSERT INTO payments (order_id, payment_status, amount) 
VALUES (3, 'PAID', 88.00) 
ON CONFLICT DO NOTHING;
