CREATE TABLE participant(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    is_confirmed BOOLEAN NOT NULL,
    trip_id INT,
    FOREIGN KEY (trip_id) REFERENCES trips(id) ON DELETE CASCADE
);