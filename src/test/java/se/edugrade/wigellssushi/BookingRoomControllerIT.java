package se.edugrade.wigellssushi;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import se.edugrade.wigellssushi.entities.Role;
import se.edugrade.wigellssushi.entities.Room;
import se.edugrade.wigellssushi.entities.Users;
import se.edugrade.wigellssushi.repositories.BookingRoomRepository;
import se.edugrade.wigellssushi.repositories.RoleRepository;
import se.edugrade.wigellssushi.repositories.RoomRepository;
import se.edugrade.wigellssushi.repositories.UserRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.test.context.TestPropertySource;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")

@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb;MODE=MySQL;DB_CLOSE_DELAY=-1;DATABASE_TO_UPPER=false",
        "spring.datasource.driverClassName=org.h2.Driver",
        "spring.datasource.username=sa",
        "spring.datasource.password=",
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect",
        "spring.sql.init.mode=never",
        "spring.flyway.enabled=false",
        "spring.liquibase.enabled=false",

        "EXCHANGE_API_KEY=dummy",
        "currency.api.key=dummy"
})

class BookingRoomControllerIT {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserRepository userRepo;
    @Autowired
    BookingRoomRepository bookingRoomRepo;
    @Autowired
    RoomRepository roomRepo;
    @Autowired
    RoleRepository roleRepo;

    Integer userId;
    Integer roomId;

    @BeforeEach
    void setUp() {
        bookingRoomRepo.deleteAll();
        userRepo.deleteAll();
        roomRepo.deleteAll();
        roleRepo.deleteAll();

        Role role = new Role();
        role.setRoleName("USER");
        role = roleRepo.save(role);

        Users user = new Users();
        user.setUserName("Test");
        user.setPassword("test");
        user.setEmail("test@test.com");
        user.setRole(role);
        userId = userRepo.save(user).getId();

        Room room = new Room();
        room.setRoomName("Test");
        room.setMaxGuests(5);
        room.setRoomPrice(new java.math.BigDecimal("500"));
        roomId = roomRepo.save(room).getId();
    }

    //"HappyPath"
    @Test
    @WithMockUser(username = "Test", roles = {"USER"})
    void bookRoom_createTestBooking() throws Exception {
        mockMvc.perform(post("/bookroom")
                .param("roomId", roomId.toString())
                .param("userId", userId.toString())
                .param("startDate", "2025-11-01")
                .param("endDate", "2025-11-02")
                .param("guests", "2"))
                .andExpect(status().isCreated());
    }

    //Invalid dates
    @Test
    @WithMockUser(username = "Test", roles = {"USER"})
    void bookRoom_endBeforeStartDate_badRq() throws Exception {
        mockMvc.perform(post("/bookroom")
                        .param("roomId", roomId.toString())
                        .param("userId", userId.toString())
                        .param("startDate", "2025-11-05")
                        .param("endDate", "2025-11-02")
                        .param("guests", "2"))
                .andExpect(status().isBadRequest());
    }

    //User not found
    @Test
    @WithMockUser(username = "Test", roles = {"USER"})
    void bookRoom_userNotFound() throws Exception {
        mockMvc.perform(post("/bookroom")
                        .param("roomId", roomId.toString())
                        .param("userId", "007")
                        .param("startDate", "2025-11-01")
                        .param("endDate", "2025-11-02")
                        .param("guests", "2"))
                .andExpect(status().isNotFound());
    }

    //Room not found
    @Test
    @WithMockUser(username = "Test", roles = {"USER"})
    void bookRoom_roomNotFound() throws Exception {
        mockMvc.perform(post("/bookroom")
                        .param("roomId", "400")
                        .param("userId",userId.toString())
                        .param("startDate", "2025-11-01")
                        .param("endDate", "2025-11-02")
                        .param("guests", "2"))
                .andExpect(status().isNotFound());
    }

    //More than max guests
    @Test
    @WithMockUser(username = "Test", roles = {"USER"})
    void bookRoom_tooManyGuests() throws Exception {
        mockMvc.perform(post("/bookroom")
                        .param("roomId", roomId.toString())
                        .param("userId", userId.toString())
                        .param("startDate", "2025-11-01")
                        .param("endDate", "2025-11-02")
                        .param("guests", "1000"))
                .andExpect(status().isBadRequest());
    }

    //Overlap booking
    @Test
    @WithMockUser(username = "Test", roles = {"USER"})
    void bookRoom_() throws Exception {
        mockMvc.perform(post("/bookroom")
                        .param("roomId", roomId.toString())
                        .param("userId", userId.toString())
                        .param("startDate", "2025-11-10")
                        .param("endDate", "2025-11-12")
                        .param("guests", "2"))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/bookroom")
                        .param("roomId", roomId.toString())
                        .param("userId", userId.toString())
                        .param("startDate", "2025-11-11")
                        .param("endDate", "2025-11-13")
                        .param("guests", "2"))
                .andExpect(status().isConflict());
    }

}
