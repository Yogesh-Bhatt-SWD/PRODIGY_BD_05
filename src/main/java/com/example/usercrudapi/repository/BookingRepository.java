package com.example.usercrudapi.repository;

import com.example.usercrudapi.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface BookingRepository extends JpaRepository<Booking, UUID> {

    List<Booking> findByGuest_Id(UUID guestId);
    
    List<Booking> findByRoom_Id(UUID roomId);

    @Query("SELECT CASE WHEN COUNT(b) > 0 THEN true ELSE false END " +
           "FROM Booking b WHERE b.room.id = :roomId " +
           "AND b.status != 'CANCELLED' " +
           "AND (b.checkInDate < :checkOutDate AND b.checkOutDate > :checkInDate)")
    boolean existsConflictingBooking(
            @Param("roomId") UUID roomId,
            @Param("checkInDate") LocalDate checkInDate,
            @Param("checkOutDate") LocalDate checkOutDate
    );
}
