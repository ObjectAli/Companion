create schema if not exists public;

CREATE TABLE users (
    -- --- Идентификация ---
    id                 UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    email              VARCHAR(255)     NOT NULL UNIQUE,
    password_hash      VARCHAR(255)     NOT NULL,

    -- --- Профиль ---
    username           VARCHAR(50)      NOT NULL UNIQUE,
    name               VARCHAR(100),
    surname            VARCHAR(100),
    birth_date         DATE,
    avatar_url         VARCHAR(512),
    about_user         TEXT,
    city               VARCHAR(100),

    -- --- Контакты ---
    phone              VARCHAR(20),
    tg_username        VARCHAR(64),

    -- --- Роль и статус ---
    status             varchar(12)      NOT NULL DEFAULT 'ACTIVE',
    email_verified     BOOLEAN          NOT NULL DEFAULT FALSE,
    phone_verified     BOOLEAN          NOT NULL DEFAULT FALSE,

    -- --- Репутация ---
    participant_rating DOUBLE PRECISION NOT NULL DEFAULT 0,
    organizer_rating   DOUBLE PRECISION NOT NULL DEFAULT 0,
    events_attended    INTEGER          NOT NULL DEFAULT 0,
    events_organized   INTEGER          NOT NULL DEFAULT 0,

    -- --- Аудит ---
    created_at         TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at         TIMESTAMP,

    -- --- Ограничения валидации ---
    CONSTRAINT chk_user_email_fmt    CHECK (email ~* '^[^@\s]+@[^@\s]+\.[^@\s]+$'),
    CONSTRAINT chk_user_rating_part  CHECK (participant_rating BETWEEN 0 AND 5),
    CONSTRAINT chk_user_rating_org   CHECK (organizer_rating   BETWEEN 0 AND 5),
    CONSTRAINT chk_user_birth_date   CHECK (birth_date IS NULL OR birth_date <= CURRENT_DATE),
    CONSTRAINT chk_events_attended_val   CHECK (events_attended >= 0),
    CONSTRAINT chk_events_organized_val  CHECK (events_organized >= 0),
    CONSTRAINT chk_user_status_val   CHECK (status IN ('ACTIVE', 'BLOCKED', 'DELETED'))
);

    CREATE INDEX idx_user_email    ON users (email);
    CREATE INDEX idx_user_username ON users (username);
    CREATE INDEX idx_user_city     ON users (city)     WHERE city IS NOT NULL;
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
    is_adults_flg BOOLEAN default false,

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
    updated_at TIMESTAMP NULL,

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