create schema if not exists public;

CREATE TABLE users (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    phone VARCHAR(15) UNIQUE,
    email VARCHAR(255) UNIQUE,
    tg_username VARCHAR(255) UNIQUE,
    full_name VARCHAR(255),
    avatar_url TEXT,
    rating DECIMAL(3,2) DEFAULT 5.0,
    role VARCHAR(50) DEFAULT 'participant',
    created_at TIMESTAMP DEFAULT NOW()
);