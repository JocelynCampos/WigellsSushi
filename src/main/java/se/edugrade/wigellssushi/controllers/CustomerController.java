package se.edugrade.wigellssushi.controllers;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.edugrade.wigellssushi.dto.OrderFoodRequest;
import se.edugrade.wigellssushi.entities.*;
import se.edugrade.wigellssushi.enums.BookingStatus;
import se.edugrade.wigellssushi.services.BookingService;


import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/wigellsushi")
public class CustomerController {

    private final BookingService bookingService;


    public CustomerController(BookingService bookingService) {
        this.bookingService = bookingService;

    }

    // -------------------- Lokaler
    @PostMapping("/bookroom")
    public ResponseEntity <BookingRoom> bookRoom(@RequestParam Integer roomId,
                                                 @RequestParam Integer userId,
                                                 @RequestParam @DateTimeFormat (iso = DateTimeFormat.ISO.DATE)LocalDate startDate,
                                                 @RequestParam @DateTimeFormat (iso = DateTimeFormat.ISO.DATE)LocalDate endDate,
                                                 @RequestParam Integer guests) {
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
