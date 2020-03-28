INSERT INTO currencies (code, decimal_places) VALUES
    ('EUR', 2),
    ('USD', 2);

INSERT INTO pricing_policies (unit_price, currency) VALUES
    (50, 'EUR'),
    (350, 'EUR');

INSERT INTO pricing_policies (unit_price, fixed_price, currency) VALUES
    (50, 100, 'EUR');

INSERT INTO parkings (name, address, city, pricing_policy) VALUES
    ('Parking de la Promenade', 'Promenade des Anglais', 'Nice', 2),
    ('Parking des Pecheurs', 'Esplanade de Pecheurs', 'Antibes', 3),
    ('Parking Monte Carlo', 'Place du Casino', 'Monaco', 1);

INSERT INTO cars (plate, car_engine_type) VALUES
    ('AA-123-AA', 'gasoline'),
    ('AB-321-BA', '20kw'),
    ('YY-897-ZZ', '50kw');

INSERT INTO park_slots (number, car_plate, car_allowed, parking_id) VALUES
    (1, 'AA-123-AA', 'gasoline', 1),
    (6, 'AB-321-BA', '20kw', 1),
    (9, 'YY-897-ZZ', '50kw', 1);

INSERT INTO park_slots (number, car_allowed, parking_id) VALUES
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