CREATE TABLE IF NOT EXISTS _USER (
                                     id INTEGER AUTO_INCREMENT PRIMARY KEY,
                                     firstname VARCHAR(255),
    lastname VARCHAR(255),
    email VARCHAR(255) UNIQUE NOT NULL,
    phone VARCHAR(20),
    password VARCHAR(255),
    role VARCHAR(20)
    );
CREATE TABLE IF NOT EXISTS CREDITO (
                                       id INTEGER AUTO_INCREMENT PRIMARY KEY,
                                       deudor_id INTEGER,
                                       cobrador_id INTEGER,
                                       saldo DOUBLE,
                                       created_at DATE,
                                       updated_at DATE,
                                       FOREIGN KEY (deudor_id) REFERENCES _USER(id),
                                       FOREIGN KEY (cobrador_id) REFERENCES _USER(id)
);


INSERT INTO _user (id, firstname, lastname, email, phone, password, role)
VALUES (1, 'John', 'Doe', 'john.doe@example.com', '123456789', '$2a$10$2yERXj5/R5PfVLEpG34h5OVsSTUVv54VjTpdW7qO3QcZk31Q8W5SW', 'ADMIN');

INSERT INTO _user (id, firstname, lastname, email, phone, password, role)
VALUES (2, 'Jane', 'Doe', 'jane.doe@example.com', '987654321', '$2a$10$5jYK7jxZS2i/dvPbtQewVOn6sUuRQ0i2IR9w1KXTgJ5D0TmweMNyK', 'DEUDOR');

INSERT INTO _user (id, firstname, lastname, email, phone, password, role)
VALUES (3, 'Alice', 'Smith', 'alice.smith@example.com', '456123789', '$2a$10$eA4Qb1M1tOsoDXH56W6yvOzXaIyPHaTB3Pfz1Y/wTt/7F1soGZdI2', 'COBRADOR');

INSERT INTO _user (id, firstname, lastname, email, phone, password, role)
VALUES (4, 'Pedro', 'Perez', 'pedro.perez@example.com', '456123789', '$2a$10$eA4Qb1M1tOsoDXH56W6yvOzXaIyPHaTB3Pfz1Y/wTt/7F1soGZdI2', 'COBRADOR');

INSERT INTO credito (id, deudor_id, cobrador_id, saldo)
VALUES (1, 2, 3, 1000.00);

INSERT INTO credito (id, deudor_id, cobrador_id, saldo)
VALUES (2, 2, 3, 500.00);

INSERT INTO credito (id, deudor_id, cobrador_id, saldo)
VALUES (3, 3, 2, 750.00);
