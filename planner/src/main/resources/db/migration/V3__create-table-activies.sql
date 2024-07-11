CREATE TABLE activies(
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    occurs_at TIMESTAMP NOT NULL,
    trips_id INT,
    FOREIGN KEY (trips_id) REFERENCES trips(id) ON DELETE CASCADE
);