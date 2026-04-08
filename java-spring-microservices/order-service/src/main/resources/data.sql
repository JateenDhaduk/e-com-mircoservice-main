INSERT INTO orders (user_id, status, total_amount, created_at) VALUES
(1, 'CONFIRMED', 164890.00, '2026-04-01T10:30:00'),
(2, 'PENDING', 12995.00, '2026-04-05T14:15:00'),
(1, 'DELIVERED', 350.00, '2026-03-20T09:00:00');

INSERT INTO order_items (order_id, product_id, product_name, quantity, unit_price) VALUES
(1, 1, 'iPhone 15 Pro', 1, 134900.00),
(1, 3, 'Sony WH-1000XM5', 1, 29990.00),
(2, 4, 'Nike Air Max 90', 1, 12995.00),
(3, 7, 'The Alchemist', 1, 350.00);
