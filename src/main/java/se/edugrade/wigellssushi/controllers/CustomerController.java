package se.edugrade.wigellssushi.controllers;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.edugrade.wigellssushi.entities.*;
import se.edugrade.wigellssushi.enums.BookingStatus;
import se.edugrade.wigellssushi.exceptions.ResourceNotFoundException;
import se.edugrade.wigellssushi.repositories.BookingRoomRepository;
import se.edugrade.wigellssushi.services.BookingService;
import se.edugrade.wigellssushi.services.MenuService;
import se.edugrade.wigellssushi.services.RoomService;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/wigellsushi")
public class CustomerController {

    private final BookingService bookingService;
    private final MenuService menuService;
    private final RoomService roomService;

    private BookingRoomRepository bookingRoomRepository;

    public CustomerController(BookingService bookingService, MenuService menuService, RoomService roomService) {
        this.bookingService = bookingService;
        this.menuService = menuService;
        this.roomService = roomService;
    }

    // -------------------- Lokaler
    @PostMapping("/bookroom")
    public ResponseEntity <BookingRoom> bookRoom(@RequestParam Integer roomId,
                                                 @RequestParam Integer userId,
                                                 @RequestParam @DateTimeFormat (iso = DateTimeFormat.ISO.DATE)LocalDate starDate,
                                                 @RequestParam @DateTimeFormat (iso = DateTimeFormat.ISO.DATE)LocalDate endDate,
                                                 @RequestParam Integer guests) {
        BookingRoom booking = bookingService.bookRoom(roomId, userId, starDate, endDate, guests);
        return ResponseEntity.status(HttpStatus.CREATED).body(booking);
    }

    /**@PostMapping("/orderfood")
    public ResponseEntity <BookingRoom> orderFood(@RequestParam Integer bookingId,
                                                  @RequestParam String username,
                                                  @RequestParam String items) {
        Map<Integer, Integer> parsed = parseItems(items);

    }
     **/

    @PutMapping("/cancelbooking")
    public ResponseEntity<Void> cancelBooking(@PathVariable Integer bookingId,
                                              @RequestParam String username) {
        bookingService.cancel(bookingId, username);
        return ResponseEntity.noContent().build();
    }


    /**
    @GetMapping("/mybookings**")
    public ResponseEntity <List<BookingRoom>> myBookings(@RequestParam Integer userId, @RequestParam String status) {
        return ResponseEntity.ok(bookingService.)
    }
     **/
}
