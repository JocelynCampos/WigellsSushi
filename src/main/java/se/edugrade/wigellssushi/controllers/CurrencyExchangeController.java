package se.edugrade.wigellssushi.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.edugrade.wigellssushi.services.CurrencyService;

@RestController
@RequestMapping("/api/wigellsushi")
public class CurrencyExchangeController {

    private final CurrencyService currencyService;
    public CurrencyExchangeController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping("/euro-to-sek")
    public float euroToSek() {
        return currencyService.getSEKtoEUR();
    }

}
