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
    seat_number VARCHAR(10),
    available BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (flight_id) REFERENCES flights(id) ON DELETE CASCADE
);

DELIMITER //
CREATE TRIGGER add_seats_after_flight_creation
AFTER INSERT ON flights
FOR EACH ROW
BEGIN
    DECLARE seat_index INT DEFAULT 1;
    WHILE seat_index <= 10 DO
        INSERT INTO seats (flight_id, seat_number, available) VALUES (NEW.id, CONCAT(seat_index), TRUE);
        SET seat_index = seat_index + 1;
    END WHILE;
END;
//
DELIMITER ;
```

Databeseyi sorgulama komutları:
```
SELECT * FROM users;
SELECT * FROM passwords;
SELECT * FROM flights;
SELECT * FROM user_flights;
SELECT * FROM seats;
```

Databaseyi temizleme komutları:
```
DELETE FROM user_flights;
DELETE FROM seats;
DELETE FROM flights;
DELETE FROM passwords;
DELETE FROM users;

ALTER TABLE users AUTO_INCREMENT = 1;
ALTER TABLE flights AUTO_INCREMENT = 1;
ALTER TABLE user_flights AUTO_INCREMENT = 1;
ALTER TABLE seats AUTO_INCREMENT = 1;
ALTER TABLE passwords AUTO_INCREMENT = 1;
```

<img src="https://github.com/can61cebi/CanAir/blob/main/Development_Stage_01.png" width="400">
