CREATE TABLE participant(
    id UUID DEFAULT RANDOM_UUID() PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    is_confirmed BOOLEAN NOT NULL,
    trips_id UUID,
    FOREIGN KEY (trips_id) REFERENCES trips(id) ON DELETE CASCADE
);