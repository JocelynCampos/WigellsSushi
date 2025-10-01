package se.edugrade.wigellssushi.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/wigellsushi")
public class CustomerController {


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
