CREATE SCHEMA IF NOT EXISTS app;
CREATE TABLE app.users
(
    id             uuid PRIMARY KEY,
    registered_at  timestamp with time zone,
    first_name     text,
    surname        text,
    email          text,
    phoneNumber    text,
    telegramUserId text,
    password       text
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

ALTER table app.users
    ADD CONSTRAINT uniq_user_email UNIQUE (email);

ALTER TABLE app.organizations
    ADD CONSTRAINT fk_owner FOREIGN KEY (owner_id) REFERENCES app.users (id);