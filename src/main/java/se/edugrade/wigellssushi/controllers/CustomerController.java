package se.edugrade.wigellssushi.controllers;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import se.edugrade.wigellssushi.dto.OrderFoodRequest;
import se.edugrade.wigellssushi.entities.*;
import se.edugrade.wigellssushi.enums.BookingStatus;
import se.edugrade.wigellssushi.repositories.RoomRepository;
import se.edugrade.wigellssushi.repositories.UserRepository;
import se.edugrade.wigellssushi.services.BookingService;


import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/wigellsushi")
public class CustomerController {

    private final BookingService bookingService;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;


    public CustomerController(BookingService bookingService, RoomRepository roomRepository, UserRepository userRepository) {
        this.bookingService = bookingService;
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
    }

    // -------------------- Lokaler
    @PostMapping("/bookroom")
    public ResponseEntity <BookingRoom> bookRoom(@RequestParam ("roomId") Integer roomId,
                                                 @RequestParam ("userId") Integer userId,
                                                 @RequestParam ("startDate") @DateTimeFormat (iso = DateTimeFormat.ISO.DATE)LocalDate startDate,
                                                 @RequestParam ("endDate")@DateTimeFormat (iso = DateTimeFormat.ISO.DATE)LocalDate endDate,
                                                 @RequestParam ("guests") Integer guests) {
        var room = roomRepository.findById(roomId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Room not found."));
        var user = userRepository.findById(userId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found."));
        if (startDate.isAfter(endDate)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Start date must be <= endDate");
        }

        BookingRoom booking = bookingService.bookRoom(roomId, userId, startDate, endDate, guests);
        return ResponseEntity.status(HttpStatus.CREATED).body(booking);
    }

    @PostMapping("/orderfood")
    public ResponseEntity <BookingRoom> orderFood(@RequestBody OrderFoodRequest foodReq) {
        BookingRoom upd = bookingService.orderFood(foodReq.getBookingId(), foodReq.getItems(), foodReq.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED).body(upd);
    }

    @PutMapping("/cancelbooking")
    public ResponseEntity<Void> cancelBooking(@RequestParam Integer bookingId,
                                              @RequestParam String username) {
        bookingService.cancel(bookingId, username);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/mybookings")
    public ResponseEntity <List<BookingRoom>> myBookings(@RequestParam String username,
                                                         @RequestParam(required = false) BookingStatus status) {
        List<BookingRoom> all = bookingService.getMyBookings(username);
        if (status != null) {
            all = all.stream().filter(b -> b.getStatus() == status).toList();
        }
        return ResponseEntity.ok(all);
    }
}
