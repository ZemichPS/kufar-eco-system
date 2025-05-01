CREATE SCHEMA IF NOT EXISTS app;
CREATE TABLE app.geos
(
    id     INTEGER NOT NULL,
    pid    INTEGER,
    name   VARCHAR(255),
    type   VARCHAR(255),
    tag    VARCHAR(255),
    region INTEGER,
    area   INTEGER,
    CONSTRAINT pk_geos PRIMARY KEY (id)
);