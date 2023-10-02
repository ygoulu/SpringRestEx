CREATE TABLE operation
(
    id            BIGSERIAL PRIMARY KEY,
    first_number  DOUBLE PRECISION NOT NULL,
    second_number DOUBLE PRECISION NOT NULL,
    percentage    DOUBLE PRECISION NOT NULL,
    result        DOUBLE PRECISION NOT NULL,
    timestamp     TIMESTAMP        NOT NULL
);

CREATE TABLE history
(
    id           SERIAL PRIMARY KEY,
    endpoint     VARCHAR(255) NOT NULL,
    timestamp    TIMESTAMP    NOT NULL,
    status       VARCHAR(255) NOT NULL,
    exception    TEXT,
    operation_id BIGINT,
    CONSTRAINT fk_operation FOREIGN KEY (operation_id) REFERENCES operation (id)
);
