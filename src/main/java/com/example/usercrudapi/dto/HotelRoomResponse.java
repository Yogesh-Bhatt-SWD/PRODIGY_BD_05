package com.example.usercrudapi.dto;

import com.example.usercrudapi.entity.HotelRoom;
import com.example.usercrudapi.entity.RoomType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class HotelRoomResponse {

    private UUID id;
    private String ownerName;
    private UUID ownerId;
    private String roomName;
    private String description;
    private RoomType roomType;
    private BigDecimal pricePerNight;
    private Integer maxGuests;
    private String city;
    private String address;
    private Boolean isAvailable;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public HotelRoomResponse(HotelRoom room) {
        this.id = room.getId();
        this.ownerName = room.getOwner().getName();
        this.ownerId = room.getOwner().getId();
        this.roomName = room.getRoomName();
        this.description = room.getDescription();
        this.roomType = room.getRoomType();
        this.pricePerNight = room.getPricePerNight();
        this.maxGuests = room.getMaxGuests();
        this.city = room.getCity();
        this.address = room.getAddress();
        this.isAvailable = room.getIsAvailable();
        this.createdAt = room.getCreatedAt();
        this.updatedAt = room.getUpdatedAt();
    }

    // Getters
    public UUID getId() { return id; }
    public String getOwnerName() { return ownerName; }
    public UUID getOwnerId() { return ownerId; }
    public String getRoomName() { return roomName; }
    public String getDescription() { return description; }
    public RoomType getRoomType() { return roomType; }
    public BigDecimal getPricePerNight() { return pricePerNight; }
    public Integer getMaxGuests() { return maxGuests; }
    public String getCity() { return city; }
    public String getAddress() { return address; }
    public Boolean getIsAvailable() { return isAvailable; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
