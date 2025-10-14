package se.edugrade.wigellssushi.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.edugrade.wigellssushi.entities.*;
import se.edugrade.wigellssushi.enums.BookingStatus;
import se.edugrade.wigellssushi.exceptions.ResourceNotFoundException;
import se.edugrade.wigellssushi.repositories.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)

public class BookingService implements BookingServiceInterface {

    private static final Logger adminLogger = LoggerFactory.getLogger(BookingService.class);

    private final BookingRoomRepository bookingRoomRepository;
    private final BookingFoodRepository bookingFoodRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final MenuRepository menuRepository;

    public BookingService(BookingRoomRepository bookingRoomRepository,
                          BookingFoodRepository bookingFoodRepository,
                          UserRepository userRepository,
                          RoomRepository roomRepository,
                          MenuRepository menuRepository) {
        this.bookingRoomRepository = bookingRoomRepository;
        this.bookingFoodRepository = bookingFoodRepository;
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
        this.menuRepository = menuRepository;
    }


    @Transactional
    @Override
    public BookingRoom bookRoom(Integer roomId, Integer userId, LocalDate startDate, LocalDate endDate, Integer guests) {
        if (startDate == null || endDate == null || !endDate.isAfter(startDate)) {
            throw new IllegalArgumentException("Invalid start or end date");
        }
        if (guests == null || guests < 0) {
            throw new IllegalArgumentException("Invalid amount of guests.");
        }

        Users user = userRepository.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("Users", "id", userId));
        Room room = roomRepository.findById(roomId)
                .orElseThrow(()-> new ResourceNotFoundException("Room", "id", roomId));

        //rum Kapacitet
        if (room.getMaxGuests() != null && guests > room.getMaxGuests()) {
            throw new IllegalArgumentException("Amounts of guests exceeds room capacity");
        }

        boolean overlap = bookingRoomRepository
                .existsByRoom_IdAndStatusAndStartDateLessThanAndEndDateGreaterThan(
                        roomId, BookingStatus.ACTIVE, endDate, startDate);

        //Om bokning kolliderar med annan existerande bokning
        if (overlap) {
            throw new IllegalArgumentException("Room already booked");
        }

        BookingRoom booked = new BookingRoom();
        booked.setUser(user);
        booked.setRoom(room);
        booked.setStartDate(startDate);
        booked.setEndDate(endDate);
        booked.setGuestCount(guests);
        booked.setStatus(BookingStatus.ACTIVE);

        BookingRoom saved = bookingRoomRepository.save(booked);
        adminLogger.info("Booked room: id={}, user={}", saved.getId(), saved.getUser().getId());

        return saved;  //Beräkna kostnad här? Använd API
    }

    @Override
    @Transactional
    public BookingRoom orderFood(Integer bookingId, Map<Integer, Integer> items, String username) {
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("No items provided");
        }

        BookingRoom booking = bookingRoomRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("BookingRoom", "id", bookingId));

        for (Map.Entry<Integer, Integer> entry : items.entrySet()) {
            Integer menuId = entry.getKey();
            Integer qty = entry.getValue();
            if (qty == null || qty <= 0) {
                throw new IllegalArgumentException("Quantity must be above 0.");
            }

            Menu menu = menuRepository.findById(menuId)
                    .orElseThrow(() -> new ResourceNotFoundException("Menu", "id", menuId));

            if (bookingFoodRepository.existsByBookingRoom_IdAndMenu_Id(bookingId, menuId)) {
                throw new IllegalArgumentException("Items already added to this booking.");
            }
            BigDecimal total = menu.getPricePerPlate()
                    .multiply(BigDecimal.valueOf(qty))
                    .setScale(2, RoundingMode.HALF_UP);

            BookingFood bookingFood = new BookingFood();
            bookingFood.setBookingRoom(booking);
            bookingFood.setMenu(menu);
            bookingFood.setQty(qty);
            bookingFood.setTotPriceSekAtBooking(total); //Ändra denna?

            bookingFoodRepository.save(bookingFood);

        }
        adminLogger.info("Added food to bookingId={}, by user={}", bookingId, username);
        return booking;
    }

    @Override
    @Transactional
    public BookingRoom cancel(Integer bookingId, String username) {
        BookingRoom bookedRoom = bookingRoomRepository.findById(bookingId)
                .orElseThrow(()-> new ResourceNotFoundException("BookingRoom", "id", bookingId));
        if (!bookedRoom.getUser().getUserName().equals(username)) {
            throw new IllegalArgumentException("Username not matched.");
        }

        LocalDate lastDateToCancel = bookedRoom.getStartDate().minusWeeks(1);
        if (LocalDate.now().isAfter(lastDateToCancel)) {
            throw new IllegalArgumentException("Can't cancel this booking due to arrival being less than 7 days from now.");
        }

        bookedRoom.setStatus(BookingStatus.CANCELED);
        BookingRoom saved = bookingRoomRepository.save(bookedRoom);
        adminLogger.info("Canceled booking room: id={}, user={}", bookingId, username);
        return saved;
    }

    @Override
    @Transactional
    public List<BookingRoom> getMyBookings(String username) {
        return bookingRoomRepository.findByUser_UserNameOrderByStartDateDesc(username);
    }


    @Override
    public List<BookingRoom> listUpcoming() {
        return bookingRoomRepository.findByStatusAndStartDateGreaterThanEqualOrderByStartDateAsc(BookingStatus.ACTIVE, LocalDate.now());
    }

    @Override
    public List<BookingRoom> listPast() {
        return bookingRoomRepository.findByEndDateBeforeOrderByStartDateDesc(LocalDate.now());
    }

    @Override
    @Transactional
    public List<BookingRoom> listCanceled() {
        return bookingRoomRepository.findByStatusOrderByStartDateDesc(BookingStatus.CANCELED);
    }
}
