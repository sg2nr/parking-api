DROP TABLE IF EXISTS currencies;

CREATE TABLE currencies (
    code VARCHAR(3) PRIMARY KEY,
    decimal_places INT NOT NULL
);

DROP TABLE IF EXISTS pricing_policies;

CREATE TABLE pricing_policies (
    id INT AUTO_INCREMENT PRIMARY KEY,
    unit_price INT NOT NULL,
    fixed_price INT DEFAULT NULL,
    currency VARCHAR(3),
    foreign key (currency) references currencies(code)
);

DROP TABLE IF EXISTS parkings;

CREATE TABLE parkings (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(250) NOT NULL,
    address VARCHAR(250) NOT NULL,
    city VARCHAR(250) NOT NULL,
    pricing_policy INT,
    foreign key (pricing_policy) references pricing_policies(id)
);

DROP TABLE IF EXISTS cars;

CREATE TYPE engine_type AS ENUM ('gasoline', '20kw', '50kw');

CREATE TABLE cars (
    plate VARCHAR(10) PRIMARY KEY,
    car_engine_type engine_type NOT NULL
);

DROP TABLE IF EXISTS park_slots;

CREATE TABLE park_slots (
    number INT NOT NULL,
    car_plate VARCHAR(10) DEFAULT NULL,
    parking_id INT,
    car_allowed engine_type DEFAULT 'gasoline',
    foreign key (parking_id) references parkings(id),
    foreign key (car_plate) references cars(plate),
    PRIMARY KEY(parking_id, number)
);