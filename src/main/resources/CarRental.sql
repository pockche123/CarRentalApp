-- Drop tables if they exist
DROP TABLE IF EXISTS booking_services;
DROP TABLE IF EXISTS booking_rentals;
DROP TABLE IF EXISTS payments;
DROP TABLE IF EXISTS cars;
DROP TABLE IF EXISTS locations;
DROP TABLE IF EXISTS customers;
DROP TABLE IF EXISTS admins;

-- Create customers table
CREATE TABLE customers (
                           customer_id SERIAL PRIMARY KEY,
                           first_name VARCHAR(255),
                           last_name VARCHAR(255),
                           address VARCHAR(255),
                           username VARCHAR(30) NOT NULL UNIQUE,
                           password VARCHAR(30) NOT NULL
);

-- Insert data into customers table
INSERT INTO customers (first_name, last_name, address, username, password)
VALUES
    ('Dafydd', 'Evans', '12 Cardiff Road, Cardiff, CF10 1AA', 'daf_evans12', 'p7T@zQfK'),
    ('Carys', 'Jones', '45 Swansea Lane, Swansea, SA1 3AB', 'carys_jones321', 'Xk3$gLvW'),
    ('Gethin', 'Thomas', '78 Newport Crescent, Newport, NP20 4EH', 'gethin_t55', 'm8R!hTp9'),
    ('Sian', 'Williams', '22 Aberystwyth Avenue, Aberystwyth, SY23 2DX', 'sian_w32', 'Bt2@qLpX'),
    ('Rhys', 'Morgan', '33 Merthyr Street, Merthyr Tydfil, CF47 8LB', 'rhys_morgan234', 'Zy9#dKwE'),
    ('Bethan', 'Hughes', '90 Llanelli Road, Llanelli, SA14 9PL', 'beth_hughes56', 'Nk6@tQzM'),
    ('Owain', 'Davies', '5 Bridgend Court, Bridgend, CF31 1NL', 'owain_d32', 'Jp4$wRxV'),
    ('Ffion', 'Roberts', '66 Caerphilly Way, Caerphilly, CF83 1HZ', 'ffion_r32', 'Lt8!zMpK');

-- Create locations table
CREATE TABLE locations (
                           location_id SERIAL PRIMARY KEY,
                           country VARCHAR(100) NOT NULL,
                           city VARCHAR(100) NOT NULL,
                           address VARCHAR(200) NOT NULL
);

-- Insert data into locations table
INSERT INTO locations (country, city, address) VALUES
                                                   ('United Kingdom', 'Cardiff', 'Cardiff Castle, Castle Street, CF10 3RB'),
                                                   ('United Kingdom', 'Swansea', 'Swansea Marina, Kings Road, SA1 1YN'),
                                                   ('United Kingdom', 'Newport', 'Newport City Centre, Bridge Street, NP20 4BG'),
                                                   ('United Kingdom', 'Brecon', 'Brecon Beacons National Park, Brecon, LD3 8DT'),
                                                   ('United Kingdom', 'Carmarthen', 'Carmarthen Castle, Castle Hill, SA31 1JP'),
                                                   ('United Kingdom', 'Aberystwyth', 'Aberystwyth Promenade, Aberystwyth, SY23 2AZ'),
                                                   ('United Kingdom', 'Llandudno', 'Llandudno Pier, North Shore, LL30 2XT');


-- Create cars table
CREATE TABLE cars (
                      registration_plate CHAR(7) PRIMARY KEY,
                      car_type VARCHAR(30) NOT NULL,
                      make VARCHAR(30) NOT NULL,
                      model VARCHAR(30) NOT NULL,
                      car_status VARCHAR(50) NOT NULL,
                      pickup_location_id INT,
                      FOREIGN KEY (pickup_location_id) REFERENCES locations(location_id)
);

-- Insert data into cars table
INSERT INTO cars (registration_plate, car_type, make, model, car_status, pickup_location_id) VALUES
                                                                                                 ('AG23CUY', 'Sedan', 'Toyota', 'Corolla', 'available', 1),
                                                                                                 ('XY23ZYH', 'SUV', 'Ford', 'Kuga', 'rented', 2),
                                                                                                 ('LM19NPU', 'Hatchback', 'Volkswagen', 'Golf', 'available', 3),
                                                                                                 ('RY21SUU', 'Convertible', 'BMW', 'Z4', 'available', 4),
                                                                                                 ('DF20JKL', 'Estate', 'Audi', 'A4', 'rented', 5),
                                                                                                 ('GH18MNO', 'Coupe', 'Mercedes-Benz', 'C-Class', 'available', 6),
                                                                                                 ('JK15YUR', 'Minivan', 'Volkswagen', 'Sharan', 'rented', 7),
                                                                                                 ('ZA75TRA', 'Estate', 'BMW', 'Golf', 'in_service', 3),
                                                                                                 ('VW12XYZ', 'Minivan', 'Toyota', 'Sienna', 'in_repair', 6),
                                                                                                 ('ZX34ABC', 'Convertible', 'Audi', 'A5', 'in_service', 7);

-- Create payments table
CREATE TABLE payments (
                          payment_id SERIAL PRIMARY KEY,
                          card_type VARCHAR(20),
                          card_number CHAR(16),
                          expiry_date CHAR (5), -- day/month
                          cvc VARCHAR(4)
);

-- Insert data into payments table
INSERT INTO payments (card_type, card_number, expiry_date, cvc) VALUES
                                                                    ('Credit Card', '4111111111111111', '12/25', '123'),
                                                                    ('Debit Card', '378282246310005', '08/27', '654'),
                                                                    ('Debit Card', '4000056655665556', '06/22', '987'),
                                                                    ('Debit Card', '5105105105105105', '05/25', '654');

-- Create booking_rentals table
CREATE TABLE booking_rentals (
                                 booking_rental_id SERIAL PRIMARY KEY,
                                 payment_id INT,
                                 registration_plate VARCHAR(7),
                                 dropoff_location_id INT,
                                 customer_id INT,
                                 suspend BOOLEAN DEFAULT false,
                                 FOREIGN KEY (payment_id) REFERENCES payments(payment_id),
                                 FOREIGN KEY (registration_plate) REFERENCES cars(registration_plate),
                                 FOREIGN KEY (dropoff_location_id) REFERENCES locations(location_id),
                                 FOREIGN KEY (customer_id) REFERENCES customers(customer_id)
);

-- Insert data into booking_rentals table
INSERT INTO booking_rentals (payment_id, registration_plate, dropoff_location_id, customer_id, suspend) VALUES
                                                                                                            (1, 'AG23CUY', 1, 1, false),
                                                                                                            (2, 'XY23ZYH', 2, 2, false),
                                                                                                            (3, 'LM19NPU', 3, 3, true),
                                                                                                            (4, 'RY21SUU', 4, 4, false),
                                                                                                            (1, 'DF20JKL', 5, 5, false),
                                                                                                            (2, 'GH18MNO', 1, 1, true),
                                                                                                            (3, 'JK15YUR', 2, 2, false);

-- Create booking_services table
CREATE TABLE booking_services (
                                  booking_services_id SERIAL PRIMARY KEY,
                                  service_type VARCHAR(30),
                                  registration_plate VARCHAR(7),
                                  service_status VARCHAR(50),
                                  FOREIGN KEY (registration_plate) REFERENCES cars(registration_plate)
);

-- Insert data into booking_services table
INSERT INTO booking_services (service_type, registration_plate, service_status) VALUES
                                                                                    ('Oil Change', 'ZA75TRA', 'Completed'),
                                                                                    ('Tire Replacement', 'VW12XYZ', 'In Progress'),
                                                                                    ('Engine Repair', 'ZX34ABC', 'Completed');



-- Create admins table
CREATE TABLE admins (
                        admin_id SERIAL PRIMARY KEY,
                        username VARCHAR(30) NOT NULL UNIQUE,
                        password VARCHAR(30) NOT NULL UNIQUE

);

-- Insert data into admins table
INSERT INTO admins (username,password) VALUES
                                           ('admin01', 'adminpass321'),
                                           ('adminjane43', 'admindasd123'),
                                           ('adminrand32', 'asdfdas123');

-- Select data from admins table
SELECT * FROM admins;

-- Select data from cars table
SELECT * FROM cars;

-- Select data from locations table
SELECT * FROM locations;

-- Select data from customers table
SELECT * FROM customers;

-- Select data from booking_rentals table
SELECT * FROM booking_rentals;

-- Select data from booking_services table
SELECT * FROM booking_services;

-- Select data from payments table
SELECT * FROM payments;
