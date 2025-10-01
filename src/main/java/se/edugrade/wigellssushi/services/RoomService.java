package se.edugrade.wigellssushi.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.edugrade.wigellssushi.entities.Room;
import se.edugrade.wigellssushi.exceptions.ResourceNotFoundException;
import se.edugrade.wigellssushi.repositories.RoomRepository;
import java.util.List;


@Service
@Transactional(readOnly = true)
public class RoomService {

    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    private static final Logger adminLogger = LoggerFactory.getLogger(RoomService.class);

    public List<Room> getListOfRooms() {
        return roomRepository.findAll();
    }

    @Transactional
    public Room addNewRoom(Room room) {
        if(roomRepository.existsByNameIgnoreCase(room.getRoomName())) {
            throw new IllegalArgumentException("Room name already exists");
        }
        Room newRoom = roomRepository.save(room);
        adminLogger.info("Added new room: id={}, name={}", newRoom.getId(), newRoom.getRoomName());
        return newRoom;
    }

    @Transactional
    public Room updateRoom(Integer id, Room patch) {
        Room existingRoom = roomRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Room", "id", id));

        if(patch.getRoomName() != null) {
            String newName = patch.getRoomName().trim();
            if (!newName.equalsIgnoreCase(existingRoom.getRoomName()) && roomRepository.existsByNameIgnoreCase(newName)) {
                throw new IllegalArgumentException("Room name already exists");
            }
            if (newName.isEmpty())
                throw new IllegalArgumentException("Room name cant be blank.");
            existingRoom.setRoomName(newName);
        }
        //if (
    }

    @Transactional
    public void deleteRoom(Integer id) {
        if (!roomRepository.existsById(id)) {
            throw new ResourceNotFoundException("Room", "id", id);
        }
        roomRepository.deleteById(id);
        adminLogger.info("Deleted room: id={} removed.", id);
    }


}
