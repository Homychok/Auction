-- liquibase formatted sql

-- changeset Homychok:1
CREATE TABLE lot (
                     lot_id      BIGINT NOT NULL,
                     lot_status  VARCHAR(255) NOT NULL,
                     lot_title VARCHAR NOT NULL,
                     lot_description VARCHAR NOT NULL,
                     lot_current_price INTEGER NOT NULL,
                     lot_last_bid  INTEGER PRIMARY KEY
);

