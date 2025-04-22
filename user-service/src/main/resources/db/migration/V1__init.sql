DROP SCHEMA IF EXISTS app CASCADE;
CREATE SCHEMA IF NOT EXISTS app;

CREATE TABLE app.userIds
(
    id               uuid PRIMARY KEY,
    role             text,
    username         text,
    registered_at    timestamp with time zone,
    first_name       text,
    last_name        text,
    email            text,
    phone_number     text,
    telegram_user_id text,
    password         text,
    organization_id  uuid

);

CREATE TABLE app.organizations
(
    id               uuid PRIMARY KEY,
    name             text,
    postal_code      text,
    region           text,
    district         text,
    city             text,
    street           text,
    house_number     text,
    apartment_number text,
    type             text,
    phone_number     text,
    owner_id         uuid
);

ALTER table app.userIds
    ADD CONSTRAINT uniq_user_email UNIQUE (email);

ALTER TABLE app.userIds ADD CONSTRAINT uniq_username UNIQUE (username);