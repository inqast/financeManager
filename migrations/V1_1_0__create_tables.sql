CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    login VARCHAR(256) UNIQUE NOT NULL,
    password_hash VARCHAR(256) NOT NULL,
    token VARCHAR(256) UNIQUE NOT NULL
);

CREATE TABLE categories (
    id SERIAL PRIMARY KEY,
    type VARCHAR(256) NOT NULL,
    name VARCHAR(256) NOT NULL,
    amount_limit INT,
    owner INT NOT NULL REFERENCES users (id),
    UNIQUE(owner, name)
);

CREATE TABLE operations (
    id SERIAL PRIMARY KEY,
    type VARCHAR(256) NOT NULL,
    amount INT,
    category_id INT NOT NULL REFERENCES categories (id),
    owner INT NOT NULL REFERENCES users (id)
);