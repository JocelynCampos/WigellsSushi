package se.edugrade.wigellssushi.controllers;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.edugrade.wigellssushi.dto.OrderFoodRequestDTO;
import se.edugrade.wigellssushi.dto.RoomBookingDTO;
import se.edugrade.wigellssushi.entities.*;
import se.edugrade.wigellssushi.enums.BookingStatus;
import se.edugrade.wigellssushi.repositories.RoomRepository;
import se.edugrade.wigellssushi.repositories.UserRepository;
import se.edugrade.wigellssushi.services.BookingService;


import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("")
public class CustomerController {

    private final BookingService bookingService;


    public CustomerController(BookingService bookingService, RoomRepository roomRepository, UserRepository userRepository) {
        this.bookingService = bookingService;
    }


    // -------------------- Lokaler
    @PostMapping("/bookroom")
    public ResponseEntity <RoomBookingDTO> bookRoom(@RequestParam Integer roomId,
                                                    @RequestParam Integer userId,
                                                    @RequestParam @DateTimeFormat (iso = DateTimeFormat.ISO.DATE)LocalDate startDate,
                                                    @RequestParam @DateTimeFormat (iso = DateTimeFormat.ISO.DATE)LocalDate endDate,
                                                    @RequestParam Integer guests) {

        BookingRoom booking = bookingService.bookRoom(roomId, userId, startDate, endDate, guests);
        return ResponseEntity.status(HttpStatus.CREATED).body(RoomBookingDTO.of(booking));
    }

    @PostMapping("/orderfood")
    public ResponseEntity <RoomBookingDTO> orderFood(@RequestBody OrderFoodRequestDTO foodReq) {
        BookingRoom upd = bookingService.orderFood(foodReq.getBookingId(), foodReq.getItems(), foodReq.getUsername());
        return ResponseEntity.ok(RoomBookingDTO.of(upd));
    }

    @PutMapping("/cancelbooking")
    public ResponseEntity<RoomBookingDTO> cancelBooking(@RequestParam Integer bookingId,
                                                        @RequestParam String username) {
        BookingRoom canceled = bookingService.cancel(bookingId, username);
        return ResponseEntity.ok(RoomBookingDTO.of(canceled));
    }

    @GetMapping("/mybookings")
    public ResponseEntity <List<RoomBookingDTO>> myBookings(@RequestParam String username,
                                                            @RequestParam(required = false) BookingStatus status) {
        List<BookingRoom> all = bookingService.getMyBookings(username);
        if (status != null) {
            all = all.stream().filter(b -> b.getStatus() == status).toList();
        }
        List<RoomBookingDTO> dtos = all.stream().map(RoomBookingDTO::of).toList();
        return ResponseEntity.ok(dtos);
    }
}
