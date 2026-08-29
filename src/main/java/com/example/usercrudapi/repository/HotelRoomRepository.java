package com.example.usercrudapi.repository;

import com.example.usercrudapi.entity.HotelRoom;
import com.example.usercrudapi.entity.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface HotelRoomRepository extends JpaRepository<HotelRoom, UUID> {

    List<HotelRoom> findByOwner_Id(UUID ownerId);

    @Query("SELECT r FROM HotelRoom r WHERE r.isAvailable = true " +
           "AND (:city IS NULL OR LOWER(r.city) LIKE LOWER(CONCAT('%', :city, '%'))) " +
           "AND (:roomType IS NULL OR r.roomType = :roomType) " +
           "AND (:minPrice IS NULL OR r.pricePerNight >= :minPrice) " +
           "AND (:maxPrice IS NULL OR r.pricePerNight <= :maxPrice) " +
           "AND (:minGuests IS NULL OR r.maxGuests >= :minGuests) " +
           "AND (:checkInDate IS NULL OR :checkOutDate IS NULL OR r.id NOT IN (" +
           "   SELECT b.room.id FROM Booking b WHERE b.status != 'CANCELLED' " +
           "   AND (b.checkInDate < :checkOutDate AND b.checkOutDate > :checkInDate)" +
           "))")
    List<HotelRoom> searchAvailableRooms(
            @Param("city") String city,
            @Param("roomType") RoomType roomType,
            @Param("minPrice") BigDecimal minPrice,
            @Param("maxPrice") BigDecimal maxPrice,
            @Param("minGuests") Integer minGuests,
            @Param("checkInDate") LocalDate checkInDate,
            @Param("checkOutDate") LocalDate checkOutDate
    );
}
