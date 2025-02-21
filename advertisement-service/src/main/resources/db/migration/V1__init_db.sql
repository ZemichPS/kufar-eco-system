CREATE SCHEMA IF NOT EXISTS app;

CREATE TABLE app.advertisements
(
    uuid            uuid,
    user_uuid       uuid,
    category_uuid   uuid,
    condition       text,
    published_at    TIMESTAMP WITHOUT TIME ZONE,
    activated_at    TIMESTAMP WITHOUT TIME ZONE,
    price_in_byn    numeric,
    comment         text,
    photo_file_name text,
    active          bool,
    CONSTRAINT pk_advertisement PRIMARY KEY (uuid)
);

CREATE TABLE app.categories
(
    uuid uuid,
    name text UNIQUE ,
    CONSTRAINT pk_categories PRIMARY KEY (uuid)
);

CREATE TABLE app.category_attributes
(
    uuid uuid,
    name text UNIQUE,
    CONSTRAINT pk_category_attributes PRIMARY KEY (uuid)
);

CREATE TABLE app.advertisement_attributes
(
    uuid                   uuid,
    advertisement_uuid     uuid,
    category_attribute_uuid uuid,
    value                  text,
    CONSTRAINT pk_advertisement_attributes PRIMARY KEY (uuid)
);

CREATE TABLE app.category_catattributes
(
    category_uuid           uuid,
    category_attribute_uuid uuid,
    CONSTRAINT pk_category_attributes_jt PRIMARY KEY (category_uuid, category_attribute_uuid)
);

ALTER TABLE app.advertisements
    ADD CONSTRAINT fk_category FOREIGN KEY (category_uuid) REFERENCES app.categories (uuid);

ALTER TABLE app.category_catattributes
    ADD CONSTRAINT fk_category FOREIGN KEY (category_uuid) REFERENCES app.categories (uuid),
    ADD CONSTRAINT fk_category_attribute FOREIGN KEY (category_attribute_uuid) REFERENCES app.category_attributes (uuid);

ALTER TABLE app.advertisement_attributes
    ADD CONSTRAINT fk_advertisement FOREIGN KEY (advertisement_uuid) REFERENCES app.advertisements (uuid),
    ADD CONSTRAINT fk_category_attribute FOREIGN KEY (category_attribute_uuid) REFERENCES app.category_attributes (uuid)