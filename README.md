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
```

Databaseyi temizleme komutları:
```
DELETE FROM passwords;
DELETE FROM users;
DELETE FROM flights;
DELETE FROM user_flights;

DELETE FROM users WHERE id = 1;
DELETE FROM users WHERE id = 2;
DELETE FROM users WHERE id = 3;
DELETE FROM users WHERE id = 4;
DELETE FROM users WHERE id = 5;
DELETE FROM users WHERE id = 6;
DELETE FROM users WHERE id = 7;
DELETE FROM users WHERE id = 8;
DELETE FROM users WHERE id = 9;
DELETE FROM users WHERE id = 10;
DELETE FROM users WHERE id = 11;
DELETE FROM users WHERE id = 12;
DELETE FROM users WHERE id = 13;
DELETE FROM users WHERE id = 14;
DELETE FROM users WHERE id = 15;
DELETE FROM users WHERE id = 16;
DELETE FROM users WHERE id = 17;
DELETE FROM users WHERE id = 18;
DELETE FROM users WHERE id = 19;
DELETE FROM users WHERE id = 20;

DELETE FROM passwords WHERE id = 1;
DELETE FROM passwords WHERE id = 2;
DELETE FROM passwords WHERE id = 3;
DELETE FROM passwords WHERE id = 4;
DELETE FROM passwords WHERE id = 5;
DELETE FROM passwords WHERE id = 6;
DELETE FROM passwords WHERE id = 7;
DELETE FROM passwords WHERE id = 8;
DELETE FROM passwords WHERE id = 9;
DELETE FROM passwords WHERE id = 10;
DELETE FROM passwords WHERE id = 11;
DELETE FROM passwords WHERE id = 12;
DELETE FROM passwords WHERE id = 13;
DELETE FROM passwords WHERE id = 14;
DELETE FROM passwords WHERE id = 15;
DELETE FROM passwords WHERE id = 16;
DELETE FROM passwords WHERE id = 17;
DELETE FROM passwords WHERE id = 18;
DELETE FROM passwords WHERE id = 19;
DELETE FROM passwords WHERE id = 20;

DELETE FROM flights WHERE id = 1;
DELETE FROM flights WHERE id = 2;
DELETE FROM flights WHERE id = 3;
DELETE FROM flights WHERE id = 4;
DELETE FROM flights WHERE id = 5;
DELETE FROM flights WHERE id = 6;
DELETE FROM flights WHERE id = 7;
DELETE FROM flights WHERE id = 8;
DELETE FROM flights WHERE id = 9;
DELETE FROM flights WHERE id = 10;
DELETE FROM flights WHERE id = 11;
DELETE FROM flights WHERE id = 12;
DELETE FROM flights WHERE id = 13;
DELETE FROM flights WHERE id = 14;
DELETE FROM flights WHERE id = 15;
DELETE FROM flights WHERE id = 16;
DELETE FROM flights WHERE id = 17;
DELETE FROM flights WHERE id = 18;
DELETE FROM flights WHERE id = 19;
DELETE FROM flights WHERE id = 20;

DELETE FROM user_flights WHERE id = 1;
DELETE FROM user_flights WHERE id = 2;
DELETE FROM user_flights WHERE id = 3;
DELETE FROM user_flights WHERE id = 4;
DELETE FROM user_flights WHERE id = 5;
DELETE FROM user_flights WHERE id = 6;
DELETE FROM user_flights WHERE id = 7;
DELETE FROM user_flights WHERE id = 8;
DELETE FROM user_flights WHERE id = 9;
DELETE FROM user_flights WHERE id = 10;
DELETE FROM user_flights WHERE id = 11;
DELETE FROM user_flights WHERE id = 12;
DELETE FROM user_flights WHERE id = 13;
DELETE FROM user_flights WHERE id = 14;
DELETE FROM user_flights WHERE id = 15;
DELETE FROM user_flights WHERE id = 16;
DELETE FROM user_flights WHERE id = 17;
DELETE FROM user_flights WHERE id = 18;
DELETE FROM user_flights WHERE id = 19;
DELETE FROM user_flights WHERE id = 20;

ALTER TABLE user_flights AUTO_INCREMENT = 1;
ALTER TABLE flights AUTO_INCREMENT = 1;
ALTER TABLE users AUTO_INCREMENT = 1;
ALTER TABLE passwords AUTO_INCREMENT = 1;
```

<img src="https://github.com/can61cebi/CanAir/blob/main/Development_Stage_01.png" width="400">
