CREATE TABLE activities(
    id UUID DEFAULT RANDOM_UUID() PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    occurs_at TIMESTAMP NOT NULL,
    trips_id UUID,
    FOREIGN KEY (trips_id) REFERENCES trips(id) ON DELETE CASCADE
);