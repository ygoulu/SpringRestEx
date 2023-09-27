CREATE TABLE history (
                         id BIGINT NOT NULL AUTO_INCREMENT,
                         num1 DOUBLE NOT NULL,
                         num2 DOUBLE NOT NULL,
                         percentage DOUBLE NOT NULL,
                         result DOUBLE NOT NULL,
                         timestamp DATETIME NOT NULL,
                         PRIMARY KEY (id)
);