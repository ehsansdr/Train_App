CREATE TABLE train (
                       id INT AUTO_INCREMENT PRIMARY KEY,   -- Primary key with auto-increment
                       name VARCHAR(255) NOT NULL,           -- Name of the train (cannot be NULL)
                       route VARCHAR(255),                   -- The route the train takes
                       departure_time TIMESTAMP,             -- The time the train departs
                       arrival_time TIMESTAMP,               -- The time the train arrives
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- Auto-generated timestamp for when the row is created
                       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP -- Auto-generated timestamp for when the row is updated
);
