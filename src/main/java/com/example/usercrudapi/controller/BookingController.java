package com.example.usercrudapi.controller;

import com.example.usercrudapi.dto.BookingRequest;
import com.example.usercrudapi.dto.BookingResponse;
import com.example.usercrudapi.entity.User;
import com.example.usercrudapi.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<BookingResponse> createBooking(@Valid @RequestBody BookingRequest request, Authentication authentication) {
        User currentUser = (User) authentication.getPrincipal();
        BookingResponse booking = bookingService.createBooking(request, currentUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(booking);
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<BookingResponse> cancelBooking(@PathVariable UUID id, Authentication authentication) {
        User currentUser = (User) authentication.getPrincipal();
        BookingResponse booking = bookingService.cancelBooking(id, currentUser);
        return ResponseEntity.ok(booking);
    }

    @GetMapping("/my")
    public ResponseEntity<List<BookingResponse>> getMyBookings(Authentication authentication) {
        User currentUser = (User) authentication.getPrincipal();
        return ResponseEntity.ok(bookingService.getMyBookings(currentUser));
    }

    @GetMapping("/room/{roomId}")
    public ResponseEntity<List<BookingResponse>> getBookingsForRoom(@PathVariable UUID roomId, Authentication authentication) {
        User currentUser = (User) authentication.getPrincipal();
        return ResponseEntity.ok(bookingService.getBookingsForRoom(roomId, currentUser));
    }
}
