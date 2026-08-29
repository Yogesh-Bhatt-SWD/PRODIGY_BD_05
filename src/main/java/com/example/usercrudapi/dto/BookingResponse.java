package com.example.usercrudapi.dto;

import com.example.usercrudapi.entity.Booking;
import com.example.usercrudapi.entity.BookingStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class BookingResponse {

    private UUID id;
    private UUID roomId;
    private String roomName;
    private UUID guestId;
    private String guestName;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private BigDecimal totalPrice;
    private BookingStatus status;
    private LocalDateTime createdAt;

    public BookingResponse(Booking booking) {
        this.id = booking.getId();
        this.roomId = booking.getRoom().getId();
        this.roomName = booking.getRoom().getRoomName();
        this.guestId = booking.getGuest().getId();
        this.guestName = booking.getGuest().getName();
        this.checkInDate = booking.getCheckInDate();
        this.checkOutDate = booking.getCheckOutDate();
        this.totalPrice = booking.getTotalPrice();
        this.status = booking.getStatus();
        this.createdAt = booking.getCreatedAt();
    }

    // Getters
    public UUID getId() { return id; }
    public UUID getRoomId() { return roomId; }
    public String getRoomName() { return roomName; }
    public UUID getGuestId() { return guestId; }
    public String getGuestName() { return guestName; }
    public LocalDate getCheckInDate() { return checkInDate; }
    public LocalDate getCheckOutDate() { return checkOutDate; }
    public BigDecimal getTotalPrice() { return totalPrice; }
    public BookingStatus getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
