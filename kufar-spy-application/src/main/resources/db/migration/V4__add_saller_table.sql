CREATE TABLE app.sellers
(
    id                        VARCHAR(200) PRIMARY KEY,
    feedback_count            INTEGER,
    first_feedback_created_at TIMESTAMP WITH TIME ZONE,
    rate                      REAL
);

ALTER TABLE app.kufarAdvertisements
    ADD seller_id VARCHAR(200);

ALTER TABLE app.kufarAdvertisements
    ADD CONSTRAINT fk_seller FOREIGN KEY (seller_id) REFERENCES app.sellers(id)

