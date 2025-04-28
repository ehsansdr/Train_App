-- liquibase formatted sql

-- changeset ehsan:1745851909481-1
CREATE TABLE card
(
    id          BIGINT       NOT NULL,
    card_number VARCHAR(255) NOT NULL,
    pin1        VARCHAR(4)   NOT NULL,
    pin2        VARCHAR(22)  NOT NULL,
    user_id     UUID,
    status      VARCHAR(20)  NOT NULL,
    created_at  TIMESTAMP WITHOUT TIME ZONE,
    updated_at  TIMESTAMP WITHOUT TIME ZONE,
    deleted_at  TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_card PRIMARY KEY (id)
);

-- changeset ehsan:1745851909481-2
ALTER TABLE card
    ADD CONSTRAINT FK_CARD_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

-- changeset ehsan:1745851909481-3
DROP SEQUENCE user_seq CASCADE;

-- changeset ehsan:1745851909481-4
DROP SEQUENCE users_seq CASCADE;

