CREATE TABLE IF NOT EXISTS bookings (
    id BINARY(16) NOT NULL,
    room_id BINARY(16) NOT NULL,
    guest_id BINARY(16) NOT NULL,
    check_in_date DATE NOT NULL,
    check_out_date DATE NOT NULL,
    total_price DECIMAL(10, 2) NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'CONFIRMED',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT fk_bookings_room FOREIGN KEY (room_id) REFERENCES hotel_rooms(id),
    CONSTRAINT fk_bookings_guest FOREIGN KEY (guest_id) REFERENCES users(id),
    CONSTRAINT chk_bookings_dates CHECK (check_out_date > check_in_date),
    INDEX idx_bookings_dates (check_in_date, check_out_date)
);
