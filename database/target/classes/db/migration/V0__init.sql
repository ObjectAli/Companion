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

-- Мероприятия
CREATE TABLE events (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    organizer_id UUID NOT NULL REFERENCES users(id),
    title VARCHAR(255) NOT NULL,
    description TEXT,
    category VARCHAR(100),  -- 'football', 'movie', 'coffee', 'hiking'
    event_time TIMESTAMP NOT NULL,
    duration_minutes INTEGER DEFAULT 60,

    -- Место (с геоданными через PostGIS)
    location_name VARCHAR(255),  -- "Стадион Лужники, 3 поле"
    location_lat DOUBLE PRECISION NOT NULL,
    location_lon DOUBLE PRECISION NOT NULL,
    location_geo GEOGRAPHY(POINT, 4326),  -- для поиска по радиусу

    price DECIMAL(10,2) NOT NULL,  -- в рублях (0 — бесплатно)
    max_participants INTEGER NOT NULL,
    current_participants INTEGER DEFAULT 0,

    status VARCHAR(50) DEFAULT 'active',  -- active, cancelled, completed
    created_at TIMESTAMP DEFAULT NOW(),

    -- Создаём пространственный индекс
    CONSTRAINT events_location_geo_check CHECK (ST_IsValid(location_geo::geometry))
);

CREATE INDEX idx_events_location_geo ON events USING GIST (location_geo);
CREATE INDEX idx_events_event_time ON events (event_time);

-- Участники мероприятия (связь + платёж)
CREATE TABLE event_participants (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    event_id UUID NOT NULL REFERENCES events(id),
    user_id UUID NOT NULL REFERENCES users(id),

    payment_id VARCHAR(255),  -- ID транзакции в ЮKassa
    amount_paid DECIMAL(10,2) NOT NULL,
    payment_status VARCHAR(50) DEFAULT 'pending',  -- pending, succeeded, refunded
    participation_status VARCHAR(50) DEFAULT 'registered',  -- registered, cancelled, attended

    registered_at TIMESTAMP DEFAULT NOW(),
    UNIQUE(event_id, user_id)
);

-- Чат мероприятия (опционально, на старте можно без)
CREATE TABLE event_messages (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    event_id UUID NOT NULL REFERENCES events(id),
    user_id UUID NOT NULL REFERENCES users(id),
    message TEXT,
    sent_at TIMESTAMP DEFAULT NOW()
);