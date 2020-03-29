DROP TABLE IF EXISTS park_slots;
DROP TABLE IF EXISTS cars;
DROP TABLE IF EXISTS parkings;
DROP TABLE IF EXISTS pricing_policies;
DROP TABLE IF EXISTS currencies;
DROP TYPE IF EXISTS engine_type;

CREATE TABLE currencies (
    code VARCHAR(3) PRIMARY KEY,
    decimal_places INT NOT NULL
);

CREATE TABLE pricing_policies (
    id IDENTITY PRIMARY KEY,
    unit_price INT NOT NULL,
    fixed_price INT DEFAULT NULL,
    currency VARCHAR(3),
    foreign key (currency) references currencies(code)
);

CREATE TABLE parkings (
    id IDENTITY PRIMARY KEY,
    name VARCHAR(250) NOT NULL,
    address VARCHAR(250) NOT NULL,
    city VARCHAR(250) NOT NULL,
    pricing_policy INT,
    foreign key (pricing_policy) references pricing_policies(id)
);

CREATE TYPE engine_type AS ENUM ('gasoline', '20kw', '50kw');

CREATE TABLE cars (
    plate VARCHAR(10) PRIMARY KEY,
    car_engine_type engine_type NOT NULL
);

CREATE TABLE park_slots (
    number INT NOT NULL,
    car_plate VARCHAR(10) DEFAULT NULL,
    parking_id INT,
    car_allowed engine_type DEFAULT 'gasoline',
    foreign key (parking_id) references parkings(id) ON DELETE CASCADE,
    foreign key (car_plate) references cars(plate),
    PRIMARY KEY(parking_id, number)
);