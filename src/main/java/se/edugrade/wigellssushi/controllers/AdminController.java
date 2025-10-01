package se.edugrade.wigellssushi.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.edugrade.wigellssushi.entities.BookingRoom;
import se.edugrade.wigellssushi.services.BookingService;

import java.util.List;

@RestController
@RequestMapping("api/wigellsushi")
public class AdminController {


    private final BookingService bookingService;


    public AdminController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping("/listcanceled")
    public ResponseEntity <List<BookingRoom>> listcanceled() {

    }

/**
• Lista avbokningar GET /api/wigellsushi/listcanceled
• Lista kommande bokningar GET /api/wigellsushi/listupcoming
• Lista historiska bokningar GET /api/wigellsushi/listpast
• Lista rätter GET /api/wigellsushi/dishes
• Lägg till rätt POST /api/wigellsushi/add-dish
• Radera rätt DELETE /api/wigellsushi/remdish/{id}
• Lista lokaler GET /api/wigellsushi/rooms
• Lägg till lokal POST /api/wigellsushi/addroom
• Uppdatera lokal PUT /api/wigellsushi/updateroom
 **/


}
