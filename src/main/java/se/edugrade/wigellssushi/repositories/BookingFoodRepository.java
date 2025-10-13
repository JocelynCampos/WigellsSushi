package se.edugrade.wigellssushi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import se.edugrade.wigellssushi.entities.BookingFood;

import java.util.List;

public interface BookingFoodRepository extends JpaRepository<BookingFood, Long> {
    boolean existsByBookingRoom_IdAndMenu_Id(Integer bookingId, Integer menuId);

    List<BookingFood> findAllByBookingRoomId(Integer bookingId);
}
