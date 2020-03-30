INSERT INTO currencies (code, decimal_places) VALUES
    ('EUR', 2),
    ('USD', 2);

INSERT INTO pricing_policies (unit_price, currency) VALUES
    (50, 'EUR'),
    (350, 'EUR');

INSERT INTO pricing_policies (unit_price, fixed_price, currency) VALUES
    (50, 100, 'EUR');

INSERT INTO parkings (parking_name, address, city, pricing_policy) VALUES
    ('Parking de la Promenade', 'Promenade des Anglais', 'Nice', 2),
    ('Parking des Pecheurs', 'Esplanade des Pecheurs', 'Antibes', 3),
    ('Parking Monte Carlo', 'Place du Casino', 'Monaco', 1);

INSERT INTO cars (plate, car_engine_type) VALUES
    ('AA-123-AA', 'gasoline'),
    ('AB-321-BA', '20kw'),
    ('YY-897-ZZ', '50kw');

INSERT INTO park_slots (slot_id, car_allowed, parking_id) VALUES
    (2, 'gasoline', 1),
    (3, 'gasoline', 1),
    (4, 'gasoline', 1),
    (5, 'gasoline', 1),
    (7, '20kw', 1),
    (8, '50kw', 1),
    (1, 'gasoline', 2),
    (2, 'gasoline', 2),
    (3, 'gasoline', 2),
    (4, 'gasoline', 2),
    (5, 'gasoline', 2),
    (6, '20kw', 2),
    (7, '20kw', 2),
    (8, '50kw', 2),
    (9, '50kw', 2),
    (1, 'gasoline', 3),
    (2, 'gasoline', 3),
    (3, 'gasoline', 3),
    (4, 'gasoline', 3),
    (5, 'gasoline', 3),
    (6, '20kw', 3),
    (7, '20kw', 3),
    (8, '50kw', 3),
    (9, '50kw', 3);

INSERT INTO parking_logs (timestamp_in, car_plate, parking_id, slot_id) VALUES
    ('2020-03-28 10:23:54+02', 'AA-123-AA', 1, 2),
    ('2020-03-29 10:23:54+02', 'AB-321-BA', 1, 7),
    ('2020-03-30 08:23:54+02', 'YY-897-ZZ', 1, 8);