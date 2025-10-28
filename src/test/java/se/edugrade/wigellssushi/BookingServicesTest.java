package se.edugrade.wigellssushi;

import org.junit.jupiter.api.BeforeEach;
import se.edugrade.wigellssushi.entities.BookingRoom;
import se.edugrade.wigellssushi.entities.Room;
import se.edugrade.wigellssushi.entities.Users;
import se.edugrade.wigellssushi.enums.BookingStatus;
import se.edugrade.wigellssushi.repositories.*;
import se.edugrade.wigellssushi.services.BookingService;
import se.edugrade.wigellssushi.services.CurrencyService;

import java.util.Optional;

import static org.mockito.Mockito.*;

public class BookingServicesTest {

    BookingRoomRepository bookingRoomRepository = mock(BookingRoomRepository.class);
    BookingFoodRepository bookingFoodRepository = mock(BookingFoodRepository.class);
    MenuRepository menuRepository = mock(MenuRepository.class);
    RoleRepository roleRepository = mock(RoleRepository.class);
    RoomRepository roomRepository= mock(RoomRepository.class);
    UserRepository userRepository = mock(UserRepository.class);
    CurrencyService currencyService= mock(CurrencyService.class);

    BookingService bookingT;

    @BeforeEach
    void setUp() {
        bookingT = new BookingService(
                bookingRoomRepository, bookingFoodRepository, userRepository,
                roomRepository, menuRepository, currencyService
        );
    }

    void bookRoom_testBooking() {
        Users user = new Users(); user.setId(1);
        Room room = new Room(); room.setId(2); room.setMaxGuests(20);

        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(roomRepository.findById(2)).thenReturn(Optional.of(room));

        when(bookingRoomRepository.existsByRoom_IdAndStatusAndStartDateLessThanAndEndDateGreaterThan(eq(2), eq(BookingStatus.ACTIVE), any(), any())).thenReturn(false);

        //when(bookingRoomRepository.save(any(BookingRoom.class))).thenAnswer()
    }


}
