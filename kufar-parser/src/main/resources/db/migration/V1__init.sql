SET timezone = 'Europe/Minsk';

CREATE SCHEMA IF NOT EXISTS app;

CREATE TABLE IF NOT EXISTS app.sellers
(
    id                        VARCHAR(200) PRIMARY KEY,
    feedback_count            INTEGER,
    first_feedback_created_at TIMESTAMP WITH TIME ZONE,
    rate                      REAL
);


CREATE TABLE IF NOT EXISTS app.kufarAdvertisements
(
    id               UUID PRIMARY KEY,
    ad_id            BIGSERIAL,
    link             TEXT,
    category         VARCHAR(100),
    company_ad       BOOLEAN,
    currency         VARCHAR(10),
    published_at     TIMESTAMP WITHOUT TIME ZONE,
    subject          TEXT,
    type             VARCHAR(10),
    price_in_byn     NUMERIC,
    price_in_usd     NUMERIC,
    details          TEXT,
    parameters       JSONB,
    fully_functional BOOLEAN,
    images           TEXT,
    seller_id        VARCHAR(200),
    CONSTRAINT fk_seller FOREIGN KEY (seller_id) REFERENCES app.sellers (id)
);

CREATE TABLE IF NOT EXISTS app.geo
(
    id     BIGSERIAL PRIMARY KEY,
    pid    BIGSERIAL,
    name   VARCHAR(100),
    type   VARCHAR(100),
    tag    TEXT,
    region INTEGER,
    area   INTEGER
);

CREATE TABLE IF NOT EXISTS app.manufactures
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS app.models
(
    id             UUID PRIMARY KEY,
    kufar_id       VARCHAR(100),
    manufacture_id BIGSERIAL,
    name           VARCHAR(100),
    FOREIGN KEY (manufacture_id) REFERENCES app.manufactures (id)
);

CREATE TABLE IF NOT EXISTS app.categories
(
    id      varchar(20) PRIMARY KEY,
    name    varchar(100),
    version varchar(10),
    ordered varchar(10)
);

CREATE TABLE IF NOT EXISTS app.subcategories
(
    id       varchar(20) PRIMARY KEY,
    parent   varchar(100),
    name     varchar(100),
    version  varchar(10),
    redirect varchar(10),
    ordered  varchar(10),
    FOREIGN KEY (parent) REFERENCES app.categories (id)
);
