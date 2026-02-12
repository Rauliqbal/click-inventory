CREATE DATABASE db_click-inventory;

use db_click-inventory;

CREATE TABLE users (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    fullname VARCHAR(100) NOT NULL,
    username VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255    ) UNIQUE NOT NULL,
    email VARCHAR(100) NOT NULL,
)