
CREATE TABLE product (
    product_id SERIAL PRIMARY KEY,
    bar_code VARCHAR(50) UNIQUE,
    product_name VARCHAR(200) NOT NULL,
    category VARCHAR(100),
    unit_measure VARCHAR(20) DEFAULT 'UN',
    cost_price DECIMAL(10,2) NOT NULL,
    sale_price DECIMAL(10,2) NOT NULL,
    amount DECIMAL(12,3) DEFAULT 0,
    min_amount DECIMAL(12,3) DEFAULT 0,
    product_enable BOOLEAN DEFAULT true,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE sale (
    sale_id SERIAL PRIMARY KEY,
    date_and_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    sale_status sale_status DEFAULT 'OPEN',
    tot_sale DECIMAL(12,2) DEFAULT 0,
    tot_discount DECIMAL(12,2) DEFAULT 0,
    tot_add DECIMAL(12,2) DEFAULT 0,
    tot_pay DECIMAL(12,2) NOT NULL,
    user_id Integer REFERENCES users(user_id),
    checkout_id Integer REFERENCES checkout(checkout_id),
    client_id Integer NULL
);

CREATE TABLE sale_item (
    item_id SERIAL PRIMARY KEY,
    sale_id Integer REFERENCES sale(sale_id) ON DELETE CASCADE,
    product_id Integer REFERENCES product(product_id),
    amount DECIMAL(12,3) NOT NULL,
    unit_price DECIMAL(10,2) NOT NULL,
    discount_price DECIMAL(10,2) DEFAULT 0,
    subtotal DECIMAL(12,2) NOT NULL
);

CREATE TABLE payment (
    payment_id SERIAL PRIMARY KEY,
    id_venda Integer REFERENCES sale(sale_id) ON DELETE CASCADE,
    payment_type payment_type NOT NULL,
    value_payment DECIMAL(12,2) NOT NULL,
    change_value DECIMAL(12,2) DEFAULT 0
);
