ALTER TABLE IF EXISTS parking_logs DROP COLUMN slot_id;
ALTER TABLE IF EXISTS parking_logs DROP COLUMN vehicle_plate;
DROP TABLE IF EXISTS parking_logs;
ALTER TABLE IF EXISTS parking_slots DROP CONSTRAINT UC_Parking_Slot;
ALTER TABLE IF EXISTS parking_slots DROP COLUMN parking_id;
DROP TABLE IF EXISTS parking_slots;
DROP TABLE IF EXISTS vehicles;
ALTER TABLE IF EXISTS parkings DROP CONSTRAINT UC_Parkings;
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
    pricing_policy INT DEFAULT 1,
    foreign key (pricing_policy) references pricing_policies(id),
    CONSTRAINT UC_Parkings UNIQUE (parking_name, city)
);

CREATE TYPE engine_type AS ENUM ('gasoline', '20kw', '50kw');

CREATE TABLE vehicles (
    plate VARCHAR(10) PRIMARY KEY,
    vehicle_engine_type engine_type NOT NULL
);

CREATE TABLE parking_slots (
    id IDENTITY PRIMARY KEY,
    slot_number INT NOT NULL,
    parking_id INT,
    vehicle_allowed engine_type DEFAULT 'gasoline',
    foreign key (parking_id) references parkings(id) ON DELETE CASCADE,
    CONSTRAINT UC_Parking_Slot UNIQUE (parking_id, slot_number)
);

CREATE TABLE parking_logs (
    id IDENTITY PRIMARY KEY,
    timestamp_in TIMESTAMP WITH TIME ZONE NOT NULL,
    timestamp_out TIMESTAMP WITH TIME ZONE,
    vehicle_plate VARCHAR(10),
    slot_id INT,
    foreign key (slot_id) references parking_slots(id) ON DELETE CASCADE,
    foreign key (vehicle_plate) references vehicles(plate) ON DELETE CASCADE
);
