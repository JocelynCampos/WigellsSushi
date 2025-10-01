package se.edugrade.wigellssushi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import se.edugrade.wigellssushi.entities.Room;

public interface RoomRepository extends JpaRepository<Room, Integer> {
}
