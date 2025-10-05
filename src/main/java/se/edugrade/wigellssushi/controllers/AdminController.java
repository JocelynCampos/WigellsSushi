package se.edugrade.wigellssushi.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.edugrade.wigellssushi.entities.BookingRoom;
import se.edugrade.wigellssushi.entities.Menu;
import se.edugrade.wigellssushi.entities.Room;
import se.edugrade.wigellssushi.services.BookingService;
import se.edugrade.wigellssushi.services.MenuService;
import se.edugrade.wigellssushi.services.RoomService;

import java.util.List;

@RestController
@RequestMapping("api/wigellsushi")
public class AdminController {


    private final BookingService bookingService;
    private final MenuService menuService;
    private final RoomService roomService;


    public AdminController(BookingService bookingService, MenuService menuService, RoomService roomService) {
        this.bookingService = bookingService;
        this.menuService = menuService;
        this.roomService = roomService;
    }

    //Se över ------------------------------------------------------------
    @GetMapping("/listcanceled")
    public ResponseEntity <List<BookingRoom>> listcanceled() {

    }

    @GetMapping("/listupcoming")
    public ResponseEntity <List<BookingRoom>> listupcoming() {
        return listupcoming();
    }

    @GetMapping("/listpast")
    public ResponseEntity <List<BookingRoom>> listpast() {
        return listpast();
    }
    //----------------------------------------------------------------------

    @PostMapping("/add-dish")
    public ResponseEntity <Menu> addDish(@RequestBody Menu menu) {
        Menu saved = menuService.addDish(menu);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @DeleteMapping("/remdish/{id}}")
    public ResponseEntity <Void> remDish(@RequestBody Integer menuId) {
        menuService.deleteDish(menuId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/addroom")
    public ResponseEntity <Room> addRoom(@RequestBody Room room) {
        Room saved = roomService.addRoom(room);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    //Se över!!!
    @PutMapping("/updateroom")
    public ResponseEntity <Room> updateRoom(@RequestBody Room room) {
        return;
    }

/**
• Lista avbokningar GET /api/wigellsushi/listcanceled
• Lista kommande bokningar GET /api/wigellsushi/listupcoming
• Lista historiska bokningar GET /api/wigellsushi/listpast
• Lista rätter GET /api/wigellsushi/dishes    BORDE INTE FINNAS HÄR OCKSÅ?
• Lägg till rätt POST /api/wigellsushi/add-dish
• Radera rätt DELETE /api/wigellsushi/remdish/{id}
• Lista lokaler GET /api/wigellsushi/rooms   BORDE INTE FINNAS HÄR OCKSÅ?
• Lägg till lokal POST /api/wigellsushi/addroom
• Uppdatera lokal PUT /api/wigellsushi/updateroom
 **/


}
