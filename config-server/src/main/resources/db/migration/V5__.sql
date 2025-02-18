CREATE TABLE app.category_attributes
(
    uuid          UUID NOT NULL,
    name          VARCHAR(255),
    category_uuid UUID,
    CONSTRAINT pk_category_attributes PRIMARY KEY (uuid)
);

ALTER TABLE app.category_attributes
    ADD CONSTRAINT FK_CATEGORY_ATTRIBUTES_ON_CATEGORY_UUID FOREIGN KEY (category_uuid) REFERENCES app.categories (uuid);