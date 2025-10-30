CREATE TABLE client (
    id BIGSERIAL PRIMARY KEY,
    type VARCHAR(50) NOT NULL,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    phone VARCHAR(50) NOT NULL,
    birth_date DATE,
    company_identifier VARCHAR(255),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    deleted BOOLEAN DEFAULT FALSE
);

CREATE TABLE contract (
    id BIGSERIAL PRIMARY KEY,
    client_id BIGINT NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE,
    cost_amount DECIMAL(19, 2) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    FOREIGN KEY (client_id) REFERENCES client(id)
);

CREATE INDEX idx_contract_client_id ON contract(client_id);
CREATE INDEX idx_contract_end_date ON contract(end_date);
CREATE INDEX idx_contract_updated_at ON contract(updated_at);