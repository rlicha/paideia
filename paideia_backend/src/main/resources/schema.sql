CREATE SCHEMA IF NOT EXISTS paideia;
SET search_path TO paideia, public;

CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE IF NOT EXISTS users
(
    id            uuid PRIMARY KEY      DEFAULT gen_random_uuid(),
    email         varchar(255) NOT NULL UNIQUE,
    username      varchar(100) NOT NULL UNIQUE,
    password_hash varchar(255) NOT NULL,
    created_at    timestamptz  NOT NULL DEFAULT now()
);

CREATE TABLE IF NOT EXISTS reading_groups
(
    id          uuid PRIMARY KEY      DEFAULT gen_random_uuid(),
    name        varchar(255) NOT NULL,
    description text,
    owner_id    uuid         NOT NULL REFERENCES users (id),
    target_date date,
    invite_code varchar(50)  NOT NULL UNIQUE,
    created_at  timestamptz  NOT NULL DEFAULT now()
);

CREATE TABLE IF NOT EXISTS group_members
(
    id        uuid PRIMARY KEY     DEFAULT gen_random_uuid(),
    group_id  uuid        NOT NULL REFERENCES reading_groups (id),
    user_id   uuid        NOT NULL REFERENCES users (id),
    role      varchar(50) NOT NULL DEFAULT 'MEMBER' CHECK (role IN ('MEMBER', 'ADMIN')),
    joined_at timestamptz NOT NULL DEFAULT now(),
    -- The same member cannot be in the same group twice
    UNIQUE (group_id, user_id)
);

CREATE TABLE IF NOT EXISTS resources
(
    id            uuid PRIMARY KEY      DEFAULT gen_random_uuid(),
    title         varchar(255) NOT NULL,
    resource_type varchar(50)  NOT NULL CHECK (resource_type IN ('BOOK', 'ARTICLE', 'BLOG')),
    author        varchar(255),
    url           text,
    isbn          varchar(20),
    created_at    timestamptz  NOT NULL DEFAULT now()
);

CREATE TABLE IF NOT EXISTS group_resources
(
    id          uuid PRIMARY KEY     DEFAULT gen_random_uuid(),
    group_id    uuid        NOT NULL REFERENCES reading_groups (id),
    resource_id uuid        NOT NULL REFERENCES resources (id),
    added_at    timestamptz NOT NULL DEFAULT now(),
    -- The same member cannot be in the same group twice
    UNIQUE (group_id, resource_id)
);

CREATE TABLE IF NOT EXISTS user_readings
(
    id          uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    group_id    uuid NOT NULL REFERENCES reading_groups (id),
    user_id     uuid NOT NULL REFERENCES users (id),
    resource_id uuid NOT NULL REFERENCES resources (id),
    started_at  timestamptz,
    status      varchar(20)      DEFAULT 'IN_PROGRESS',
    -- Updated unique constraint to allow one record per user per resource in a group
    UNIQUE (group_id, user_id, resource_id)
);

CREATE TABLE IF NOT EXISTS reading_progress
(
    id              uuid PRIMARY KEY     DEFAULT gen_random_uuid(),
    user_reading_id uuid        NOT NULL REFERENCES user_readings (id),
    progress_pct    integer     NOT NULL CHECK (progress_pct BETWEEN 0 AND 100),
    note            text,
    reported_at     timestamptz NOT NULL DEFAULT now()
);

-- Performance Indexes
CREATE INDEX idx_group_members_user ON group_members (user_id);
CREATE INDEX idx_user_readings_user ON user_readings (user_id, group_id);