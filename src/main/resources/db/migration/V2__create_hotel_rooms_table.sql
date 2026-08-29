CREATE TABLE IF NOT EXISTS hotel_rooms (
    id BINARY(16) NOT NULL,
    owner_id BINARY(16) NOT NULL,
    room_name VARCHAR(150) NOT NULL,
    description TEXT,
    room_type VARCHAR(30) NOT NULL,
    price_per_night DECIMAL(10, 2) NOT NULL,
    max_guests INT NOT NULL,
    city VARCHAR(100) NOT NULL,
    address VARCHAR(255) NOT NULL,
    is_available BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT fk_hotel_rooms_owner FOREIGN KEY (owner_id) REFERENCES users(id),
    INDEX idx_hotel_rooms_city (city),
    INDEX idx_hotel_rooms_type (room_type)
);
