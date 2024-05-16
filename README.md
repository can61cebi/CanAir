# CanAir Görsel Tabanlı Programlama Dersi Projesi

Bu proje CanAir adlı uydurulmuş bir uçak seyahat firmasının bilet yönetim programını yapmak amaçlı oluşturulmuştur.

Databaseyi oluşturma komutları:
``` 
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100)
);

CREATE TABLE passwords (
    user_id INT,
    password VARCHAR(255),
    PRIMARY KEY (user_id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

ALTER TABLE users AUTO_INCREMENT = 1;
ALTER TABLE passwords AUTO_INCREMENT = 1;

CREATE TABLE flights (
    id INT AUTO_INCREMENT PRIMARY KEY,
    departure_city VARCHAR(50),
    arrival_city VARCHAR(50),
    flight_date DATE
);

CREATE TABLE user_flights (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    flight_id INT,
    seat_choice VARCHAR(10),
    travel_insurance BOOLEAN,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (flight_id) REFERENCES flights(id)
);

CREATE TABLE seats (
    id INT AUTO_INCREMENT PRIMARY KEY,
    flight_id INT,
    seat_number VARCHAR(5),
    is_occupied BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (flight_id) REFERENCES flights(id)
);

ALTER TABLE user_flights
ADD UNIQUE KEY `unique_seat_per_flight_per_user` (`user_id`, `flight_id`);
```

Databaseyi temizleme komutları:
```
DELETE FROM user_flights;
DELETE FROM seats;
DELETE FROM passwords;

DELETE FROM users;
DELETE FROM flights;

ALTER TABLE user_flights AUTO_INCREMENT = 1;
ALTER TABLE seats AUTO_INCREMENT = 1;
ALTER TABLE passwords AUTO_INCREMENT = 1;
ALTER TABLE users AUTO_INCREMENT = 1;
ALTER TABLE flights AUTO_INCREMENT = 1;
```

<img src="https://github.com/can61cebi/CanAir/blob/main/Development_Stage_01.png" width="400">
