package se.edugrade.wigellssushi.services;

import se.edugrade.wigellssushi.entities.BookingRoom;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface BookingServiceInterface {

    //User
    BookingRoom bookRoom(Integer roomId, Integer userId, LocalDate startDate, LocalDate endDate, Integer guests);
    BookingRoom orderFood(Integer bookingId, Map<Integer, Integer> items, String username);
    BookingRoom cancel(Integer bookingId, String username);
    List<BookingRoom> getMyBookings(String username);

    //Admin
    List<BookingRoom> listUpcoming();
    List<BookingRoom> listPast();
    List<BookingRoom> listCanceled();


}
