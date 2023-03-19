-- liquibase formatted sql

-- changeset Homychok:1
CREATE TABLE picture (
                        picture_id         BIGINT PRIMARY KEY,
                        preview       BYTEA,
                        file_path  VARCHAR(255),
                        file_size  BIGINT,
                        media_type VARCHAR(255),
                        lot_id BIGINT REFERENCES lot (lot_id)
);
ALTER TABLE picture ALTER COLUMN picture_id ADD GENERATED BY DEFAULT AS IDENTITY;