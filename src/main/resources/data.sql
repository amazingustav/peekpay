INSERT INTO customers (id, email) VALUES ('b14c1af4-5842-479a-8cfb-e1f2741cc6b8', 'amazin@gust.av');

INSERT INTO orders (id, customer_id, original_value, balance) VALUES ('ae7e1cde-ab69-4583-b864-a6586dd4ebe1', 'b14c1af4-5842-479a-8cfb-e1f2741cc6b8', 1000.0, 100.0);

INSERT INTO payments (id, amount, order_id, idempotency_key) VALUES ('8c2364b5-05b4-4318-b678-e15a8ea1f7c2', 50.0, 'ae7e1cde-ab69-4583-b864-a6586dd4ebe1', null);
