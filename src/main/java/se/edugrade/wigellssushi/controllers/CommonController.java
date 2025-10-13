package se.edugrade.wigellssushi.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.edugrade.wigellssushi.entities.Menu;
import se.edugrade.wigellssushi.entities.Room;
import se.edugrade.wigellssushi.services.MenuService;
import se.edugrade.wigellssushi.services.RoomService;

import java.util.List;


@RestController
@RequestMapping("/api/wigellsushi")
public class CommonController {

    private final MenuService menuService;
    private final RoomService roomService;

    public CommonController(MenuService menuService, RoomService roomService) {
        this.menuService = menuService;
        this.roomService = roomService;
    }


    @GetMapping("/dishes")
    public ResponseEntity<List<Menu>> dishes() {
        List<Menu> dishes = menuService.getAllDishes();
        return ResponseEntity.ok(menuService.getAllDishes());
    }

    @GetMapping("/rooms")
    public ResponseEntity <List<Room>> rooms() {
        List<Room> rooms = roomService.getAllRooms();
        return ResponseEntity.ok(roomService.getAllRooms());
    }
}
