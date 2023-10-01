CREATE TABLE operation
(
    id            BIGSERIAL PRIMARY KEY,
    first_number  DOUBLE PRECISION NOT NULL,
    second_number DOUBLE PRECISION NOT NULL,
    percentage    DOUBLE PRECISION NOT NULL,
    result        DOUBLE PRECISION NOT NULL,
    timestamp     TIMESTAMP        NOT NULL
);