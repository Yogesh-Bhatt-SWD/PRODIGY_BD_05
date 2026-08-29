package com.example.usercrudapi.controller;

import com.example.usercrudapi.dto.HotelRoomRequest;
import com.example.usercrudapi.dto.HotelRoomResponse;
import com.example.usercrudapi.dto.RoomSearchRequest;
import com.example.usercrudapi.entity.User;
import com.example.usercrudapi.service.HotelRoomService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/rooms")
public class HotelRoomController {

    private final HotelRoomService hotelRoomService;

    public HotelRoomController(HotelRoomService hotelRoomService) {
        this.hotelRoomService = hotelRoomService;
    }

    @PostMapping
    public ResponseEntity<HotelRoomResponse> createRoom(@Valid @RequestBody HotelRoomRequest request, Authentication authentication) {
        User currentUser = (User) authentication.getPrincipal();
        HotelRoomResponse createdRoom = hotelRoomService.createRoom(request, currentUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRoom);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HotelRoomResponse> updateRoom(@PathVariable UUID id, @Valid @RequestBody HotelRoomRequest request, Authentication authentication) {
        User currentUser = (User) authentication.getPrincipal();
        HotelRoomResponse updatedRoom = hotelRoomService.updateRoom(id, request, currentUser);
        return ResponseEntity.ok(updatedRoom);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable UUID id, Authentication authentication) {
        User currentUser = (User) authentication.getPrincipal();
        hotelRoomService.deleteRoom(id, currentUser);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<HotelRoomResponse> getRoomById(@PathVariable UUID id) {
        return ResponseEntity.ok(hotelRoomService.getRoomById(id));
    }

    @GetMapping("/my")
    public ResponseEntity<List<HotelRoomResponse>> getMyRooms(Authentication authentication) {
        User currentUser = (User) authentication.getPrincipal();
        return ResponseEntity.ok(hotelRoomService.getMyRooms(currentUser));
    }

    @PostMapping("/search")
    public ResponseEntity<List<HotelRoomResponse>> searchRooms(@RequestBody RoomSearchRequest request) {
        return ResponseEntity.ok(hotelRoomService.searchRooms(request));
    }
}
