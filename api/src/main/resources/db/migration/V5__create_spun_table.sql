CREATE TABLE customer (
    customer_id SERIAL PRIMARY KEY,
    nome VARCHAR(200) NOT NULL,
    cpf_cnpj VARCHAR(18) UNIQUE,
    phone VARCHAR(20),
    address TEXT,
    credit_limit DECIMAL(12,2) DEFAULT 0,
    debit_balance DECIMAL(12,2) DEFAULT 0,
    enable BOOLEAN DEFAULT true
);


CREATE TABLE spun_payment (
    spun_payment_id SERIAL PRIMARY KEY,
    customer_id Integer REFERENCES customer(customer_id),
    id_venda Integer REFERENCES sale(sale_id),
    original_value DECIMAL(12,2) NOT NULL,
    current_value DECIMAL(12,2) NOT NULL,
    late_date DATE NOT NULL,
    payment_date TIMESTAMP,
    fees DECIMAL(10,4) DEFAULT 0,
    status VARCHAR(20) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
