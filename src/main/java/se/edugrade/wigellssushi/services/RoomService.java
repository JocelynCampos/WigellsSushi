package se.edugrade.wigellssushi.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import se.edugrade.wigellssushi.entities.Room;
import se.edugrade.wigellssushi.exceptions.ResourceNotFoundException;
import se.edugrade.wigellssushi.repositories.RoomRepository;
import java.util.List;


@Service
@Transactional(readOnly = true)
public class RoomService implements RoomServiceInterface {

    private static final Logger adminLogger = LoggerFactory.getLogger(RoomService.class);

    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    @Override
    @Transactional
    public Room addRoom(Room room) {
        String roomName = room.getRoomName() == null ? "" : room.getRoomName().trim();
        if (roomName.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Room name cannot be empty");
        }
        if(roomRepository.existsByRoomNameIgnoreCase(roomName)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Room name already exists");
        }
        room.setRoomName(roomName);
        Room newRoom = roomRepository.save(room);
        adminLogger.info("Added new room: id={}, name={}", newRoom.getId(), newRoom.getRoomName());
        return newRoom;
    }

    @Override
    @Transactional
    public Room updateRoom(Integer id, Room patch) {
        Room existingRoom = roomRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Room not found with id" + id));

        if(patch.getRoomName() != null) {
            String newName = patch.getRoomName().trim();

            if (newName.isEmpty())
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Room name cant be blank.");

            if (!newName.equalsIgnoreCase(existingRoom.getRoomName())
                    && roomRepository.existsByRoomNameIgnoreCase(newName)) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Room name already exists");
            }
            existingRoom.setRoomName(newName);
        }
        if (patch.getMaxGuests() != null) {
            Integer newMaxGuests = patch.getMaxGuests();
            if (patch.getMaxGuests() <= 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Max guests must be greater than 0");
            }
            existingRoom.setMaxGuests(newMaxGuests);
        }
        Room updatedRoom = roomRepository.save(existingRoom);
        adminLogger.info("Updated room: id={}, name={}", updatedRoom.getId(), updatedRoom.getRoomName());
        return updatedRoom;
    }

    @Override
    @Transactional
    public void deleteRoom(Integer id) {
        if (!roomRepository.existsById(id)) {
            throw new ResourceNotFoundException("Room", "id", id);
        }
        roomRepository.deleteById(id);
        adminLogger.info("Deleted room: id={} removed.", id);
    }


}
