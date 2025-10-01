package se.edugrade.wigellssushi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import se.edugrade.wigellssushi.entities.BookingRoom;
import se.edugrade.wigellssushi.enums.BookingStatus;

import java.time.LocalDate;
import java.util.List;

public interface BookingRoomRepository extends JpaRepository<BookingRoom, Integer> {

    boolean existsByBookingId(int bookingId);

    boolean existsByRoom_IdStatusAndStartDateLessThanAndEndDateGreaterThan
            (Integer roomId, BookingStatus status, LocalDate endDate, LocalDate startDate);

    //User: mina bokningar
    List<BookingRoom>findByUser_UserNameOrderByStartDateDesc(String userName);

    List<BookingRoom> findByUser_IdOrderByStartDateDesc(Integer userId);

    //ADmin
    List<BookingRoom>findByStatusAndStartDateGreaterThanEqualOrderByStartDateAsc(BookingStatus status, LocalDate fromDate);
    List<BookingRoom> findByEndDateBeforeOrderByStartDateDesc(LocalDate untilDate);
    List<BookingRoom> findByStatusOrderByStartDateDesc(BookingStatus status);
}
