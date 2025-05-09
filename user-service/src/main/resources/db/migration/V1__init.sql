DROP SCHEMA IF EXISTS app CASCADE;
CREATE SCHEMA IF NOT EXISTS app;

CREATE TABLE app.users
(
    id                uuid PRIMARY KEY,
    role              text,
    username          text,
    registration_date timestamp with time zone,
    first_name        text,
    last_name         text,
    email             text,
    phone_number      text,
    telegram_user_id  text,
    password          text,
    organization_id   uuid,
    enabled           bool
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

CREATE TABLE app.confirmation_codes
(
    uuid       UUID,
    user_uuid  UUID,
    email      VARCHAR(255),
    code       VARCHAR(255),
    expires_at TIMESTAMP WITHOUT TIME ZONE,
    confirmed  BOOLEAN NOT NULL,
    CONSTRAINT pk_confirmation_codes PRIMARY KEY (uuid)
);

CREATE TABLE app.identities
(
    id               UUID NOT NULL,
    user_id          UUID,
    provider_name    VARCHAR(255),
    provider_user_id VARCHAR(255),
    CONSTRAINT pk_identities PRIMARY KEY (id)
);

ALTER table app.users
    ADD CONSTRAINT uniq_user_email UNIQUE (email);

ALTER TABLE app.users
    ADD CONSTRAINT uniq_username UNIQUE (username);

ALTER TABLE app.identities
    ADD CONSTRAINT FK_IDENTITIES_ON_USER_UUID FOREIGN KEY (user_id) REFERENCES app.users (id);