CREATE TABLE `client` (
    `id` INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `limit` INTEGER NOT NULL,
    `balance` INTEGER NOT NULL
);

CREATE TABLE `transaction` (
    `id` INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `client_id` INTEGER NOT NULL,
    `value` INTEGER NOT NULL,
    `type` CHAR(1) NOT NULL,
    `description` CHAR(10) NOT NULL,
    `transaction_date` TIMESTAMP NOT NULL,
    FOREIGN KEY (`client_id`) REFERENCES `client`(`id`)
);

INSERT INTO client (`id`, `limit`, `balance`) VALUES
    (1,100000,0),
    (2,80000,0),
    (3,1000000,0),
    (4,10000000,0),
    (5,500000,0);