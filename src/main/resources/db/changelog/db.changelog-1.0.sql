CREATE TABLE users
(
    id             SERIAL PRIMARY KEY,
    image_filename VARCHAR(128),
    nickname       VARCHAR(32) UNIQUE NOT NULL,
    email VARCHAR(128) UNIQUE NOT NULL,
    password VARCHAR(128) NOT NULL
);