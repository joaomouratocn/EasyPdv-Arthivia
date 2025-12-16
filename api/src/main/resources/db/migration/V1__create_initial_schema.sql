CREATE TABLE store (
    store_id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    cnpj VARCHAR(18) UNIQUE NOT NULL,
    address TEXT,
    phone VARCHAR(20),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE checkout(
    checkout_id SERIAL PRIMARY KEY,
    name_checkout VARCHAR(50) NOT NULL,
    store_id BIGINT REFERENCES store(store_id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
