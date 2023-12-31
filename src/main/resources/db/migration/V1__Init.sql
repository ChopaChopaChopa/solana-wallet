CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS users(
    id         UUID NOT NULL DEFAULT uuid_generate_v4(),
    username   VARCHAR(32) NOT NULL,
    password   VARCHAR(512) NOT NULL,
    address    VARCHAR(44) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT current_timestamp,
    pk         VARCHAR(128) NOT NULL
);