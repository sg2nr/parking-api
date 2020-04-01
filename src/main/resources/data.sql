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
    ('Parking des Pecheurs', 'Esplanade des Pecheurs', 'Antibes', 1),
    ('Parking Monte Carlo', 'Place du Casino', 'Monaco', 3);

INSERT INTO vehicles (plate, vehicle_engine_type) VALUES
    ('AA123AA', 'gasoline'),
    ('AB321BA', '20kw'),
    ('YY897ZZ', '50kw'),
    ('NO897ZZ', 'gasoline'),
    ('06897ZZ', 'gasoline'),
    ('KL009IY', '20kw'),
    ('TP89653', '20kw');

INSERT INTO parking_slots (slot_number, vehicle_allowed, parking_id) VALUES
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

INSERT INTO parking_logs (timestamp_in, vehicle_plate, slot_id) VALUES
    ('2020-03-31 10:23:15+02', 'AA123AA', 1),
    ('2020-03-30 10:23:45+02', 'AB321BA', 5),
    ('2020-04-01 08:23:23+02', 'YY897ZZ', 6),
    ('2020-04-01 10:25:54+02', 'NO897ZZ', 7),
    ('2020-04-01 09:45:47+02', '06897ZZ', 8),
    ('2020-04-01 07:23:54+02', 'KL009IY', 12),
    ('2020-04-01 14:23:57+02', 'TP89653', 21);
