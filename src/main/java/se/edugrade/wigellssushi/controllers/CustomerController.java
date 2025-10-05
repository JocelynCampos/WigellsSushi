package se.edugrade.wigellssushi.controllers;

import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
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

import java.util.List;

@RestController
@RequestMapping("/api/wigellsushi")
public class CustomerController {

    @Autowired
    private final BookingService bookingService;
    @Autowired
    private MenuService menuService;
    @Autowired
    private RoomService roomService;
    @Autowired
    private BookingRoomRepository bookingRoomRepository;

    public CustomerController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping("/dishes")
    public ResponseEntity<List<Menu>> dishes() {
        List<Menu> dishes = menuService.getAllDishes();
        return ResponseEntity.ok(dishes);
    }

    @GetMapping("/rooms")
    public ResponseEntity <List<Room>> rooms() {
        List<Room> rooms = roomService.getAllRooms();
        return ResponseEntity.ok(rooms);
    }

    @PostMapping("/bookroom")
    public ResponseEntity <BookingRoom> bookRoom(@RequestParam Integer roomId,
                                                 @RequestParam Integer userId) {

    }

   // @PostMapping("/orderfood");
    // public ResponseEntity <Menu> () {}

    @PutMapping("/cancelbooking")
    public void cancelBooking(Integer bookingId) {
        BookingRoom booking = bookingRoomRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking", "id", bookingId));
        if (booking.getStatus() == BookingStatus.ACTIVE && booking ==) {}
    }


    @GetMapping("/mybookings**")







    /**
     * Kunderna ska kunna göra ett antal aktiviteter med följande endpoints:
     * • Lista rätter GET /api/wigellsushi/dishes
     * • Lista lokaler GET /api/wigellsushi/rooms
     * • Reservera lokal POST /api/wigellsushi/bookroom
     * • Lägga beställning på rätter POST /api/wigellsushi/orderfood
     * • Avboka lokal (fram tills en vecka innan avsatt datum) PUT
     * /api/wigellsushi/cancelbooking
     * • Se tidigare och aktiva bokningar GET /api/wigellsushi/mybookings**/
}
