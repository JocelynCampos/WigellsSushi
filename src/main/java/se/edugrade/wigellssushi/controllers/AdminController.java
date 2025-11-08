package se.edugrade.wigellssushi.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.edugrade.wigellssushi.dto.RoomBookingDTO;
import se.edugrade.wigellssushi.entities.Menu;
import se.edugrade.wigellssushi.entities.Room;
import se.edugrade.wigellssushi.services.BookingService;
import se.edugrade.wigellssushi.services.MenuService;
import se.edugrade.wigellssushi.services.RoomService;

import java.util.List;

@RestController
@RequestMapping("")
public class AdminController {


    private final BookingService bookingService;
    private final MenuService menuService;
    private final RoomService roomService;


    public AdminController(BookingService bookingService, MenuService menuService, RoomService roomService) {
        this.bookingService = bookingService;
        this.menuService = menuService;
        this.roomService = roomService;
    }

    //----------------- Bokningar -----------------
    @GetMapping("/listcanceled")
    public ResponseEntity <List<RoomBookingDTO>> listcanceled() {
        var list = bookingService.listCanceled();
        return ResponseEntity.ok(list.stream().map(RoomBookingDTO::of).toList());
    }

    @GetMapping("/listupcoming")
    public ResponseEntity <List<RoomBookingDTO>> listupcoming() {
        var list = bookingService.listUpcoming();
        return ResponseEntity.ok(list.stream().map(RoomBookingDTO::of).toList());
    }

    @GetMapping("/listpast")
    public ResponseEntity <List<RoomBookingDTO>> listpast() {
        var list = bookingService.listPast();
        return ResponseEntity.ok(list.stream().map(RoomBookingDTO::of).toList());
    }

    //-----------------------Rätter------------------------

    @PostMapping("/add-dish")
    public ResponseEntity <Menu> addDish(@RequestBody Menu menu) {
        Menu saved = menuService.addDish(menu);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @DeleteMapping("/remdish/{id}")
    public ResponseEntity <Void> remDish(@PathVariable Integer id) {
        menuService.deleteDish(id);
        return ResponseEntity.noContent().build();
    }

    //-----------------------Lokaler------------------------

    @PostMapping("/addroom")
    public ResponseEntity <Room> addRoom(@RequestBody Room room) {
        Room saved = roomService.addRoom(room);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/updateroom/{id}")
    public ResponseEntity <Room> updateRoom(@PathVariable Integer id, @RequestBody Room room) {
        Room updated = roomService.updateRoom(id, room);
        return ResponseEntity.ok(updated);
    }
}
