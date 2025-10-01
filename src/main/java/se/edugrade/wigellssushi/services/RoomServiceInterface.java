package se.edugrade.wigellssushi.services;

import se.edugrade.wigellssushi.entities.Room;

import java.util.List;

public interface RoomServiceInterface {
    List<Room> getAllRooms();
    Room addRoom(Room room);
    Room updateRoom(Integer id, Room patch);
    void deleteRoom(Integer id);

}
