package se.edugrade.wigellssushi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import se.edugrade.wigellssushi.entities.BookingFood;

public interface BookingFoodRepository extends JpaRepository<BookingFood, Long> {
}
