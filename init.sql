CREATE DATABASE weather CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE weather;

CREATE TABLE city (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    country_code CHAR(2) NOT NULL,
    latitude DECIMAL(9,6) NOT NULL,
    longitude DECIMAL(9,6) NOT NULL,
    UNIQUE (name, country_code)
);

CREATE TABLE weather_snapshot (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    city_id BIGINT NOT NULL,
    temperature DECIMAL(5,2),
    temperature_perceived DECIMAL(5,2),
    pressure INT,
    humidity INT,
    wind_speed DECIMAL(5,2),
	index_UV INT,
    description VARCHAR(255),
    icon VARCHAR(10),
    recorded_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (city_id) REFERENCES city(id) ON DELETE CASCADE
);


-- INSERTY: miasta
INSERT INTO city (id, name, country_code, latitude, longitude) VALUES
(1, 'Lublin', 'PL', 51.2465, 22.5684),
(2, 'Warsaw', 'PL', 52.2298, 21.0118),
(3, 'New York', 'US', 40.7128, -74.0060),
(4, 'London', 'GB', 51.5074, -0.1278);

-- INSERTY: historia pogody dla Lublina (city_id = 1)
-- INSERTY: dane pogodowe (weather_snapshot)
INSERT INTO weather_snapshot (city_id, temperature, temperature_perceived, pressure, humidity, wind_speed, index_UV, description, icon, recorded_at) VALUES
-- Lublin
(1, 18.55, 18.00, 1012, 60, 3.5, 5, 'Partly cloudy', '03d', '2025-05-06 12:00:00'),
(1, 12.20, 11.00, 1015, 70, 2.1, 2, 'Clear sky', '01n', '2025-05-05 23:00:00'),

-- Warsaw
(2, 20.10, 19.50, 1010, 55, 4.8, 6, 'Sunny', '01d', '2025-05-06 12:00:00'),
(2, 14.30, 13.00, 1013, 65, 3.0, 3, 'Clear sky', '01n', '2025-05-05 23:00:00'),

-- New York
(3, 22.70, 22.10, 1008, 58, 5.6, 7, 'Few clouds', '02d', '2025-05-06 12:00:00'),
(3, 17.20, 16.00, 1009, 62, 3.9, 4, 'Clear sky', '01n', '2025-05-05 23:00:00'),

-- London
(4, 16.80, 16.30, 1016, 75, 4.2, 3, 'Light rain', '10d', '2025-05-06 12:00:00'),
(4, 11.50, 10.80, 1018, 80, 2.8, 1, 'Overcast clouds', '04n', '2025-05-05 23:00:00');



INSERT INTO weather_snapshot (city_id, temperature, temperature_perceived, pressure, humidity, wind_speed, index_UV, description, icon, recorded_at) VALUES
(3, 25.70, 22.10, 1008, 58, 5.6, 7, 'sunny', '02d', '2025-05-09 14:00:00'),
(3, 19.20, 16.00, 1009, 62, 3.9, 4, 'Clear sky', '01n', '2025-05-10 22:00:00');

CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    sub VARCHAR(255) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL DEFAULT 'ROLE_USER'
);
