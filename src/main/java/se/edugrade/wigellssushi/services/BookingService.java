package se.edugrade.wigellssushi.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import se.edugrade.wigellssushi.dto.TotalCost;
import se.edugrade.wigellssushi.entities.*;
import se.edugrade.wigellssushi.enums.BookingStatus;
import se.edugrade.wigellssushi.exceptions.ResourceNotFoundException;
import se.edugrade.wigellssushi.repositories.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
@Transactional(readOnly = true)

public class BookingService implements BookingServiceInterface {

    private static final Logger adminLogger = LoggerFactory.getLogger(BookingService.class);

    private final BookingRoomRepository bookingRoomRepository;
    private final BookingFoodRepository bookingFoodRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final MenuRepository menuRepository;
    private final CurrencyService currencyService;

    public BookingService(BookingRoomRepository bookingRoomRepository,
                          BookingFoodRepository bookingFoodRepository,
                          UserRepository userRepository,
                          RoomRepository roomRepository,
                          MenuRepository menuRepository, CurrencyService currencyService) {
        this.bookingRoomRepository = bookingRoomRepository;
        this.bookingFoodRepository = bookingFoodRepository;
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
        this.menuRepository = menuRepository;
        this.currencyService = currencyService;
    }


    @Transactional
    @Override
    public BookingRoom bookRoom(Integer roomId, Integer userId, LocalDate startDate, LocalDate endDate, Integer guests) {
        if (startDate == null || endDate == null || !endDate.isAfter(startDate)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid start or end date");
        }
        if (guests == null || guests <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid amount of guests.");
        }

        Users user = userRepository.findById(userId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found: " + userId));
        Room room = roomRepository.findById(roomId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Room id not found" + roomId));

        //rum Kapacitet
        if (room.getMaxGuests() != null && guests > room.getMaxGuests()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Amounts of guests exceeds room capacity");
        }

        boolean overlap = bookingRoomRepository
                .existsByRoom_IdAndStatusAndStartDateLessThanAndEndDateGreaterThan(
                        roomId, BookingStatus.ACTIVE, endDate, startDate);

        //Om bokning kolliderar med annan existerande bokning
        if (overlap) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Room already booked");
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

        return saved;
    }

    @Override
    @Transactional
    public BookingRoom orderFood(Integer bookingId, Map<Integer, Integer> items, String username) {
        if (items == null || items.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"No items provided");
        }

        BookingRoom booking = bookingRoomRepository.findById(bookingId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Booking not found"));

        for (Map.Entry<Integer, Integer> entry : items.entrySet()) {
            Integer menuId = entry.getKey();
            Integer qty = entry.getValue();
            if (qty == null || qty <= 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Quantity must be above 0.");
            }

            Menu menu = menuRepository.findById(menuId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Menu not found:" + menuId));

            if (bookingFoodRepository.existsByBookingRoom_IdAndMenu_Id(bookingId, menuId)) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Items already added to this booking.");
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
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Booking not found:" + bookingId));
        if (!bookedRoom.getUser().getUserName().equals(username)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Username not matched.");
        }

        LocalDate lastDateToCancel = bookedRoom.getStartDate().minusWeeks(1);
        if (LocalDate.now().isAfter(lastDateToCancel)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,"Can't cancel this booking due to arrival being less than 7 days from now.");
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

    public TotalCost getTotalCost(Integer bookingId, String currency) {
        BookingRoom booking = bookingRoomRepository.findById(bookingId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Couldn't find booking."));

        long days = DAYS.between(booking.getStartDate(), booking.getEndDate()) + 1;
        if (days <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "End date must be same or after start date.");
        }

        //Tot rum kostnad
        BigDecimal roomCostSek = booking.getRoom()
                .getRoomPrice()
                .multiply(BigDecimal.valueOf(days));

        //Tot mat kostnad
        BigDecimal foodCostSek = bookingFoodRepository
                .findByBookingRoom_Id(bookingId)
                .stream()
                .map(BookingFood::getTotPriceSekAtBooking)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        //Totalt för allt i sek
        BigDecimal totSek = roomCostSek.add(foodCostSek).setScale(2, RoundingMode.HALF_UP);


        String targetCurrency = (currency == null || currency.isBlank())
                ? "SEK" : currency.trim().toUpperCase();

        BigDecimal totalInTarget;
        switch (targetCurrency) {
            case "SEK" -> totalInTarget = totSek;
            case "EUR" -> {
                float sekPerEur = currencyService.getSEKtoEUR();
                totalInTarget = totSek.divide(BigDecimal.valueOf(sekPerEur), 2, RoundingMode.HALF_UP);
            }
            default -> throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Currency not supported." + targetCurrency);
        }

        return new TotalCost(
                roomCostSek.setScale(2,RoundingMode.HALF_UP),
                foodCostSek.setScale(2,RoundingMode.HALF_UP),
                targetCurrency,
                totSek,
                totalInTarget
        );
    }
}
