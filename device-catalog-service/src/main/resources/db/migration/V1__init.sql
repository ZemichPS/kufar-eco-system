DROP SCHEMA app CASCADE;
CREATE SCHEMA app;

CREATE TABLE app.brands
(
    uuid uuid PRIMARY KEY,
    name text
);

CREATE TABLE app.models
(
    uuid       uuid PRIMARY KEY,
    name       text,
    brand_uuid uuid,
    CONSTRAINT brand_fk FOREIGN KEY (brand_uuid) references app.brands (uuid)
);