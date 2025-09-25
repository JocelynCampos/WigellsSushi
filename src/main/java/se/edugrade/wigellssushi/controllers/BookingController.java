package se.edugrade.wigellssushi.controllers;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookingController {
    /**      Kunder
    Kunderna ska kunna göra ett antal aktiviteter med följande endpoints:


    • Lista rätter GET /api/wigellsushi/dishes
• Lista lokaler GET /api/wigellsushi/rooms
• Reservera lokal POST /api/wigellsushi/bookroom
• Lägga beställning på rätter POST /api/wigellsushi/orderfood
• Avboka lokal (fram tills en vecka innan avsatt datum) PUT
/api/wigellsushi/cancelbooking
• Se tidigare och aktiva bokningar GET /api/wigellsushi/mybookings
            Admin
    Administratör ska kunna göra ett antal aktiviteter med följande endpoints:
            • Lista avbokningar GET /api/wigellsushi/listcanceled
• Lista kommande bokningar GET /api/wigellsushi/listupcoming
• Lista historiska bokningar GET /api/wigellsushi/listpast
• Lista rätter GET /api/wigellsushi/dishes
• Lägg till rätt POST /api/wigellsushi/add-dish
• Radera rätt DELETE /api/wigellsushi/remdish/{id}
• Lista lokaler GET /api/wigellsushi/rooms
• Lägg till lokal POST /api/wigellsushi/addroom
• Uppdatera lokal PUT /api/wigellsushi/updateroom**/



}
