package se.edugrade.wigellssushi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf( csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/wigellsushi/**").permitAll()

                )
                .headers(headers -> headers
                        .frameOptions(frameOptions -> frameOptions.disable())
                ) //Detta är väl dock för H2? jag har inte H2
                .httpBasic(Customizer.withDefaults());
        return http.build();

    }



    @Bean
    public InMemoryUserDetailsManager InMemoryUserDetailsManager() {
        UserDetails Admin = User.builder()
                .username("Hugo")
                .password("hugo") //Behövs "{noop-admin}"
                .roles("ADMIN")
                .build();


        UserDetails userErik = User.builder()
                .username("Erik")
                .password("erik")
                .roles("USER")
                .build();

        UserDetails userJocelyn = User.builder()
                .username("Jocelyn")
                .password("jocelyn")
                .roles("USER")
                .build();

        UserDetails userMohamed = User.builder()
                .username("Mohamed")
                .password("mohamed")
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(Admin, userErik, userJocelyn, userMohamed);
    }
}

/****************Kunderna ska kunna göra ett antal aktiviteter med följande endpoints:
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
 • Uppdatera lokal PUT /api/wigellsushi/updateroom*****************/