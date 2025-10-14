package se.edugrade.wigellssushi.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/wigell/test")
public class PingController {

    @GetMapping("/ping")
    public String ping() {
        return "Ping";
    }
}