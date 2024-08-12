INSERT INTO orders (customer_name, order_status, total_amount) 
VALUES ('John Doe', 'PENDING', 100.00) 
ON CONFLICT DO NOTHING;

INSERT INTO orders (customer_name, order_status, total_amount) 
VALUES ('Add Smith', 'PAID', 74.90) 
ON CONFLICT DO NOTHING;

INSERT INTO orders (customer_name, order_status, total_amount) 
VALUES ('Alex Mare', 'APPROVED', 88.00) 
ON CONFLICT DO NOTHING;