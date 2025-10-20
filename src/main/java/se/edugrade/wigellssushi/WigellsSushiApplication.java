package se.edugrade.wigellssushi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class WigellsSushiApplication {

    public static void main(String[] args) {
        SpringApplication.run(WigellsSushiApplication.class, args);
    }

}
