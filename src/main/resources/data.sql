INSERT INTO products (id, name, price, stock, version) VALUES
  (1, 'Keyboard', 450000, 100, 0),
  (2, 'Mouse', 250000, 200, 0),
  (3, 'Monitor', 2500000, 50, 0),
  (4, 'USB-C Cable', 120000, 500, 0),
  (5, 'Laptop Stand', 390000, 150, 0)
ON CONFLICT (id) DO NOTHING;
