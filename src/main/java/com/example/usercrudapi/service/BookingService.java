package com.example.usercrudapi.service;

import com.example.usercrudapi.dto.BookingRequest;
import com.example.usercrudapi.dto.BookingResponse;
import com.example.usercrudapi.entity.Booking;
import com.example.usercrudapi.entity.BookingStatus;
import com.example.usercrudapi.entity.HotelRoom;
import com.example.usercrudapi.entity.Role;
import com.example.usercrudapi.entity.User;
import com.example.usercrudapi.exception.BookingConflictException;
import com.example.usercrudapi.exception.ResourceNotFoundException;
import com.example.usercrudapi.repository.BookingRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final HotelRoomService hotelRoomService;

    public BookingService(BookingRepository bookingRepository, HotelRoomService hotelRoomService) {
        this.bookingRepository = bookingRepository;
        this.hotelRoomService = hotelRoomService;
    }

    public BookingResponse createBooking(BookingRequest request, User guest) {
        HotelRoom room = hotelRoomService.getHotelRoomEntity(request.getRoomId());

        if (!room.getIsAvailable()) {
            throw new BookingConflictException("This room is not currently available for booking.");
        }

        if (request.getCheckInDate().isAfter(request.getCheckOutDate()) || request.getCheckInDate().isEqual(request.getCheckOutDate())) {
            throw new IllegalArgumentException("Check-out date must be after check-in date.");
        }

        boolean conflict = bookingRepository.existsConflictingBooking(
                room.getId(), request.getCheckInDate(), request.getCheckOutDate());

        if (conflict) {
            throw new BookingConflictException("The room is already booked for the selected dates.");
        }

        long nights = ChronoUnit.DAYS.between(request.getCheckInDate(), request.getCheckOutDate());
        BigDecimal totalPrice = room.getPricePerNight().multiply(BigDecimal.valueOf(nights));

        Booking booking = new Booking();
        booking.setRoom(room);
        booking.setGuest(guest);
        booking.setCheckInDate(request.getCheckInDate());
        booking.setCheckOutDate(request.getCheckOutDate());
        booking.setTotalPrice(totalPrice);
        booking.setStatus(BookingStatus.CONFIRMED);

        Booking savedBooking = bookingRepository.save(booking);
        return new BookingResponse(savedBooking);
    }

    public BookingResponse cancelBooking(UUID bookingId, User currentUser) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + bookingId));

        if (currentUser.getRole() != Role.ADMIN && !booking.getGuest().getId().equals(currentUser.getId())) {
            throw new AccessDeniedException("You can only cancel your own bookings");
        }

        if (booking.getStatus() == BookingStatus.CANCELLED) {
            throw new IllegalArgumentException("Booking is already cancelled.");
        }

        booking.setStatus(BookingStatus.CANCELLED);
        Booking updatedBooking = bookingRepository.save(booking);
        return new BookingResponse(updatedBooking);
    }

    public List<BookingResponse> getMyBookings(User guest) {
        return bookingRepository.findByGuest_Id(guest.getId())
                .stream()
                .map(BookingResponse::new)
                .collect(Collectors.toList());
    }

    public List<BookingResponse> getBookingsForRoom(UUID roomId, User owner) {
        HotelRoom room = hotelRoomService.getHotelRoomEntity(roomId);

        if (owner.getRole() != Role.ADMIN && !room.getOwner().getId().equals(owner.getId())) {
            throw new AccessDeniedException("You can only view bookings for your own rooms");
        }

        return bookingRepository.findByRoom_Id(roomId)
                .stream()
                .map(BookingResponse::new)
                .collect(Collectors.toList());
    }
}
