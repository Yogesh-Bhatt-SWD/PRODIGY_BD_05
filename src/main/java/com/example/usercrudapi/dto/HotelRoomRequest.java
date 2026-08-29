package com.example.usercrudapi.dto;

import com.example.usercrudapi.entity.RoomType;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public class HotelRoomRequest {

    @NotBlank(message = "Room name is required")
    @Size(min = 3, max = 150, message = "Room name must be between 3 and 150 characters")
    private String roomName;

    private String description;

    @NotNull(message = "Room type is required")
    private RoomType roomType;

    @NotNull(message = "Price per night is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price per night must be greater than 0")
    private BigDecimal pricePerNight;

    @NotNull(message = "Max guests is required")
    @Min(value = 1, message = "Max guests must be at least 1")
    private Integer maxGuests;

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "Address is required")
    private String address;

    public HotelRoomRequest() {}

    // Getters and Setters
    public String getRoomName() { return roomName; }
    public void setRoomName(String roomName) { this.roomName = roomName; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public RoomType getRoomType() { return roomType; }
    public void setRoomType(RoomType roomType) { this.roomType = roomType; }
    public BigDecimal getPricePerNight() { return pricePerNight; }
    public void setPricePerNight(BigDecimal pricePerNight) { this.pricePerNight = pricePerNight; }
    public Integer getMaxGuests() { return maxGuests; }
    public void setMaxGuests(Integer maxGuests) { this.maxGuests = maxGuests; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
}
