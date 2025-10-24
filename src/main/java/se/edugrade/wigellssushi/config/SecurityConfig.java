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

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf( csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/test",
                                "/euro-to-sek").permitAll()

                        .requestMatchers("/rooms",
                                "/dishes",
                                "/bookroom",
                                "/orderfood",
                                "/cancelbooking",
                                "/mybookings",
                                "/totalcost").hasAnyRole("ADMIN", "USER")

                        .requestMatchers("/listcanceled",
                                "/listupcoming",
                                "/listpast",
                                "/add-dish",
                                "/remdish/**",
                                "/addroom",
                                "/updateroom/**")
                        .hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults());
        return http.build();

    }

    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        UserDetails Admin = User.builder()
                .username("Hugo")
                .password("{noop}hugo")
                .roles("ADMIN")
                .build();


        UserDetails userErik = User.builder()
                .username("Erik")
                .password("{noop}erik")
                .roles("USER")
                .build();

        UserDetails userJocelyn = User.builder()
                .username("Jocelyn")
                .password("{noop}jocelyn")
                .roles("USER")
                .build();

        UserDetails userMohamed = User.builder()
                .username("Mohamed")
                .password("{noop}mohamed")
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(Admin, userErik, userJocelyn, userMohamed);
    }
}