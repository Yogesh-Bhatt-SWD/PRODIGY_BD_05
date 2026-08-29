package com.example.usercrudapi.dto;

import com.example.usercrudapi.entity.RoomType;
import java.math.BigDecimal;
import java.time.LocalDate;

public class RoomSearchRequest {
    
    private String city;
    private RoomType roomType;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private Integer minGuests;

    public RoomSearchRequest() {}

    // Getters and Setters
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public RoomType getRoomType() { return roomType; }
    public void setRoomType(RoomType roomType) { this.roomType = roomType; }
    public LocalDate getCheckInDate() { return checkInDate; }
    public void setCheckInDate(LocalDate checkInDate) { this.checkInDate = checkInDate; }
    public LocalDate getCheckOutDate() { return checkOutDate; }
    public void setCheckOutDate(LocalDate checkOutDate) { this.checkOutDate = checkOutDate; }
    public BigDecimal getMinPrice() { return minPrice; }
    public void setMinPrice(BigDecimal minPrice) { this.minPrice = minPrice; }
    public BigDecimal getMaxPrice() { return maxPrice; }
    public void setMaxPrice(BigDecimal maxPrice) { this.maxPrice = maxPrice; }
    public Integer getMinGuests() { return minGuests; }
    public void setMinGuests(Integer minGuests) { this.minGuests = minGuests; }
}
