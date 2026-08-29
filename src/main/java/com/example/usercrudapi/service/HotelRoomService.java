package com.example.usercrudapi.service;

import com.example.usercrudapi.dto.HotelRoomRequest;
import com.example.usercrudapi.dto.HotelRoomResponse;
import com.example.usercrudapi.dto.RoomSearchRequest;
import com.example.usercrudapi.entity.HotelRoom;
import com.example.usercrudapi.entity.Role;
import com.example.usercrudapi.entity.User;
import com.example.usercrudapi.exception.ResourceNotFoundException;
import com.example.usercrudapi.repository.HotelRoomRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class HotelRoomService {

    private final HotelRoomRepository hotelRoomRepository;

    public HotelRoomService(HotelRoomRepository hotelRoomRepository) {
        this.hotelRoomRepository = hotelRoomRepository;
    }

    @CacheEvict(value = "rooms", allEntries = true)
    public HotelRoomResponse createRoom(HotelRoomRequest request, User owner) {
        if (owner.getRole() != Role.OWNER) {
            throw new AccessDeniedException("Only owners can create room listings");
        }

        HotelRoom room = new HotelRoom();
        room.setOwner(owner);
        updateRoomFields(room, request);
        room.setIsAvailable(true);

        HotelRoom savedRoom = hotelRoomRepository.save(room);
        return new HotelRoomResponse(savedRoom);
    }

    @Caching(evict = {
            @CacheEvict(value = "rooms", allEntries = true),
            @CacheEvict(value = "room", key = "#roomId")
    })
    public HotelRoomResponse updateRoom(UUID roomId, HotelRoomRequest request, User currentUser) {
        HotelRoom room = getHotelRoomEntity(roomId);

        // Only the owner of the room or an ADMIN can update it
        if (currentUser.getRole() != Role.ADMIN && !room.getOwner().getId().equals(currentUser.getId())) {
            throw new AccessDeniedException("You can only update your own room listings");
        }

        updateRoomFields(room, request);
        HotelRoom updatedRoom = hotelRoomRepository.save(room);
        return new HotelRoomResponse(updatedRoom);
    }

    @Caching(evict = {
            @CacheEvict(value = "rooms", allEntries = true),
            @CacheEvict(value = "room", key = "#roomId")
    })
    public void deleteRoom(UUID roomId, User currentUser) {
        HotelRoom room = getHotelRoomEntity(roomId);

        if (currentUser.getRole() != Role.ADMIN && !room.getOwner().getId().equals(currentUser.getId())) {
            throw new AccessDeniedException("You can only delete your own room listings");
        }

        hotelRoomRepository.delete(room);
    }

    @Cacheable(value = "room", key = "#roomId")
    public HotelRoomResponse getRoomById(UUID roomId) {
        return new HotelRoomResponse(getHotelRoomEntity(roomId));
    }

    // internal helper to get entity
    public HotelRoom getHotelRoomEntity(UUID roomId) {
        return hotelRoomRepository.findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel room not found with id: " + roomId));
    }

    public List<HotelRoomResponse> getMyRooms(User owner) {
        return hotelRoomRepository.findByOwner_Id(owner.getId())
                .stream()
                .map(HotelRoomResponse::new)
                .collect(Collectors.toList());
    }

    @Cacheable(value = "rooms", key = "#request.hashCode()")
    public List<HotelRoomResponse> searchRooms(RoomSearchRequest request) {
        List<HotelRoom> rooms = hotelRoomRepository.searchAvailableRooms(
                request.getCity(),
                request.getRoomType(),
                request.getMinPrice(),
                request.getMaxPrice(),
                request.getMinGuests(),
                request.getCheckInDate(),
                request.getCheckOutDate()
        );
        
        return rooms.stream()
                .map(HotelRoomResponse::new)
                .collect(Collectors.toList());
    }

    private void updateRoomFields(HotelRoom room, HotelRoomRequest request) {
        room.setRoomName(request.getRoomName());
        room.setDescription(request.getDescription());
        room.setRoomType(request.getRoomType());
        room.setPricePerNight(request.getPricePerNight());
        room.setMaxGuests(request.getMaxGuests());
        room.setCity(request.getCity());
        room.setAddress(request.getAddress());
    }
}
