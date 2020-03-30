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
    parking_name VARCHAR(250) NOT NULL,
    address VARCHAR(250) NOT NULL,
    city VARCHAR(250) NOT NULL,
    pricing_policy INT,
    foreign key (pricing_policy) references pricing_policies(id),
    CONSTRAINT UC_Parkings UNIQUE (parking_name, city)
);

CREATE TYPE engine_type AS ENUM ('gasoline', '20kw', '50kw');

CREATE TABLE cars (
    plate VARCHAR(10) PRIMARY KEY,
    car_engine_type engine_type NOT NULL
);

CREATE TABLE park_slots (
    slot_id INT NOT NULL,
    parking_id INT,
    car_allowed engine_type DEFAULT 'gasoline',
    foreign key (parking_id) references parkings(id) ON DELETE CASCADE,
    PRIMARY KEY(parking_id, slot_id)
);

CREATE TABLE parking_logs (
    id IDENTITY PRIMARY KEY,
    timestamp_in TIMESTAMP WITH TIME ZONE NOT NULL,
    timestamp_out TIMESTAMP WITH TIME ZONE,
    car_plate VARCHAR(10),
    parking_id INT,
    slot_id INT,
    foreign key (parking_id, slot_id) references park_slots(parking_id, slot_id) ON DELETE CASCADE,
    foreign key (car_plate) references cars(plate) ON DELETE CASCADE
);