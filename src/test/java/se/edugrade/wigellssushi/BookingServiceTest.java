package se.edugrade.wigellssushi;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import se.edugrade.wigellssushi.entities.BookingRoom;
import se.edugrade.wigellssushi.entities.Room;
import se.edugrade.wigellssushi.entities.Users;
import se.edugrade.wigellssushi.enums.BookingStatus;
import se.edugrade.wigellssushi.repositories.*;
import se.edugrade.wigellssushi.services.BookingService;
import se.edugrade.wigellssushi.services.CurrencyService;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BookingServiceTest {

    BookingRoomRepository bookingRoomRepository = mock(BookingRoomRepository.class);
    BookingFoodRepository bookingFoodRepository = mock(BookingFoodRepository.class);
    MenuRepository menuRepository = mock(MenuRepository.class);
    RoomRepository roomRepository= mock(RoomRepository.class);
    UserRepository userRepository = mock(UserRepository.class);
    CurrencyService currencyService= mock(CurrencyService.class);

    BookingService service;

    @BeforeEach
    void setUp() {
        service = new BookingService(
                bookingRoomRepository, bookingFoodRepository, userRepository,
                roomRepository, menuRepository, currencyService
        );
    }

    @Test
    void bookRoom_testBooking() {
        Users user = new Users(); user.setId(1);
        Room room = new Room(); room.setId(2); room.setMaxGuests(20);

        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(roomRepository.findById(2)).thenReturn(Optional.of(room));

        when(bookingRoomRepository.existsByRoom_IdAndStatusAndStartDateLessThanAndEndDateGreaterThan(eq(2),
                eq(BookingStatus.ACTIVE), any(), any()))
                .thenReturn(false);

        when(bookingRoomRepository.save(any(BookingRoom.class)))
                .thenAnswer(inv -> {BookingRoom br = inv.getArgument(0);
                    br.setId(7);
                    return br;
                });

        var saved = service.bookRoom(2,1,
                LocalDate.of(2025, 11, 1),
                LocalDate.of(2025, 11, 2),
                5);

        assertThat(saved.getId()).isEqualTo(7);
        assertThat(saved.getStatus()).isEqualTo(BookingStatus.ACTIVE);
        verify(bookingRoomRepository).save(any());
    }

    @Test
    void bookRoom_InvalidDates() {
        var ex = assertThrows(ResponseStatusException.class,
                () -> service.bookRoom(2,1,
                        LocalDate.of(2025, 11, 2),
                        LocalDate.of(2025, 11, 1),
                        5));
        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
        assertTrue(ex.getReason().contains("Invalid start or end date"));
    }

    @Test
    void bookRoom_InvalidAmountOfGuests() {
        var ex = assertThrows(ResponseStatusException.class,
                () -> service.bookRoom(2,1,
                        LocalDate.of(2025, 11, 1),
                        LocalDate.of(2025, 11, 2),
                        0));
        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
        assertTrue(ex.getReason().contains("Invalid amount of guests"));
    }

    @Test
    void bookRoom_tooManyGuests() {
        var user = new Users(); user.setId(1);
        Room room = new Room(); room.setId(2); room.setMaxGuests(4);
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(roomRepository.findById(2)).thenReturn(Optional.of(room));

        var ex = assertThrows(ResponseStatusException.class,
                ()-> service.bookRoom(2,1,
                        LocalDate.of(2025,11,1),
                        LocalDate.of(2025,11,2),
                        5));

        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
        assertTrue(ex.getReason().contains("Amounts of guests exceeds room capacity"));
    }

    @Test
    void bookRoom_IfAlreadyBooked() {
        Users user = new Users(); user.setId(1);
        Room room = new Room(); room.setId(2); room.setMaxGuests(20);

        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(roomRepository.findById(2)).thenReturn(Optional.of(room));
        when(bookingRoomRepository.existsByRoom_IdAndStatusAndStartDateLessThanAndEndDateGreaterThan(
                eq(2), eq(BookingStatus.ACTIVE), any(), any())).thenReturn(true);

        var ex = assertThrows(ResponseStatusException.class,
                () -> service.bookRoom(2,1, LocalDate.of(2025, 11, 1), LocalDate.of(2025, 11, 2), 5));
        assertEquals(HttpStatus.CONFLICT, ex.getStatusCode());
        assertTrue(ex.getReason().contains("Room already booked"));
    }

}
