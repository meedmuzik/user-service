--liquibase formatted sql

-- --changeset dezzzl:1123
-- CREATE TABLE users
-- (
--     id             SERIAL PRIMARY KEY,
--     image_filename VARCHAR(128),
--     nickname       VARCHAR(32) UNIQUE NOT NULL,
--     email VARCHAR(128) UNIQUE NOT NULL,
--     password VARCHAR(128) NOT NULL
-- );

--changeset dezzzl:11
CREATE TABLE users
(
    id             SERIAL PRIMARY KEY,
    image_filename VARCHAR(128),
    keycloak_id VARCHAR(128)
);