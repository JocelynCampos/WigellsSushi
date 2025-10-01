package se.edugrade.wigellssushi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import se.edugrade.wigellssushi.entities.BookingRoom;

public interface BookingRoomRepository extends JpaRepository<BookingRoom, Integer> {
}
