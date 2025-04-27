CREATE SCHEMA IF NOT EXISTS app;

CREATE TABLE app.advertisements
(
    uuid            uuid,
    user_uuid       uuid,
    category_uuid   uuid,
    condition       text,
    published_at    TIMESTAMP WITHOUT TIME ZONE,
    reactivated_At  TIMESTAMP WITHOUT TIME ZONE,
    price_in_byn    numeric,
    comment         text,
    photo_file_name text,
    active          bool,
    side            text,
    CONSTRAINT pk_advertisement PRIMARY KEY (uuid)
);

CREATE TABLE app.categories
(
    uuid uuid,
    name text,
    CONSTRAINT pk_category PRIMARY KEY (uuid),
    CONSTRAINT unique_category_name UNIQUE (name)
);

CREATE TABLE app.advertisement_attributes
(
    uuid                    uuid,
    advertisement_uuid      uuid,
    category_attribute_uuid uuid,
    value                   text,
    CONSTRAINT pk_advertisement_attributes PRIMARY KEY (uuid)
);

CREATE TABLE app.category_attributes
(
    uuid          uuid,
    category_uuid uuid,
    name          text,
    CONSTRAINT pk_category_attributes PRIMARY KEY (uuid)
);


ALTER TABLE app.advertisements
    ADD CONSTRAINT fk_category FOREIGN KEY (category_uuid) REFERENCES app.categories (uuid);

ALTER TABLE app.advertisement_attributes
    ADD CONSTRAINT fk_advertisement FOREIGN KEY (advertisement_uuid) REFERENCES app.advertisements (uuid),
    ADD CONSTRAINT fk_category_attribute FOREIGN KEY (category_attribute_uuid) REFERENCES app.category_attributes (uuid);

ALTER TABLE app.category_attributes
    ADD CONSTRAINT fk_category FOREIGN KEY (category_uuid) REFERENCES app.categories (uuid);