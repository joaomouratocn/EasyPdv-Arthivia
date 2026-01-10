CREATE TABLE stock (
    movement_id SERIAL PRIMARY KEY,
    product_id Integer REFERENCES product(product_id),
    user_id Integer REFERENCES users(user_id),
    sale_id Integer REFERENCES sale(sale_id),
    payment_type VARCHAR(20) NOT NULL,
    amount DECIMAL(12,3) NOT NULL,
    unit_price DECIMAL(10,2),
    description_stock TEXT,
    date_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE checkout_balance (
    checkout_balance_id SERIAL PRIMARY KEY,
    checkout_id BIGINT REFERENCES checkout(checkout_id),
    user_id BIGINT REFERENCES users(user_id),
    open_date TIMESTAMP,
    close_date TIMESTAMP,
    open_value DECIMAL(12,2),
    close_value DECIMAL(12,2),
    balance DECIMAL(12,2) DEFAULT 0
);
