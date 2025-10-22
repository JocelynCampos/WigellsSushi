package se.edugrade.wigellssushi.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import se.edugrade.wigellssushi.dto.TotalCost;
import se.edugrade.wigellssushi.services.BookingService;
import se.edugrade.wigellssushi.services.CurrencyService;

@RestController
@RequestMapping("/api/wigellsushi")
public class CurrencyExchangeController {

    private final CurrencyService currencyService;
    private final BookingService bookingService;

    public CurrencyExchangeController(CurrencyService currencyService, BookingService bookingService) {
        this.currencyService = currencyService;
        this.bookingService = bookingService;
    }

    @GetMapping("/euro-to-sek")
    public float euroToSek() {
        return currencyService.getSEKtoEUR();
    }

    @GetMapping("/totalcost")
    public TotalCost totalCost(@RequestParam Integer bookingId,
                               @RequestParam(required = false) String currency) {
        return bookingService.getTotalCost(bookingId, currency);
    }
}
