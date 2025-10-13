package se.edugrade.wigellssushi.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "booking_food", uniqueConstraints = @UniqueConstraint(name = "uq_booking_menu", columnNames = {"booking_id", "menu_item_id"}))

public class BookingFood {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "booking_id", nullable = false)
    private BookingRoom bookingRoom;

    @ManyToOne(optional = false)
    @JoinColumn(name = "menu_item_id", nullable = false)
    private Menu menu;

    @Column(name = "qty", nullable = false)
    private Integer qty;

    @Column(name = "tot_price_sek_at_booking", nullable = false, precision = 10, scale = 2)
    private BigDecimal totPriceSekAtBooking;

    public BookingFood () {}

    public BookingFood(BookingRoom bookingRoom, Menu menu, Integer qty, BigDecimal totPriceSekAtBooking) {
        this.bookingRoom = bookingRoom;
        this.menu = menu;
        this.qty = qty;
        this.totPriceSekAtBooking = totPriceSekAtBooking;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BookingRoom getBookingRoom() {
        return bookingRoom;
    }

    public void setBookingRoom(BookingRoom bookingRoom) {
        this.bookingRoom = bookingRoom;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public BigDecimal getTotPriceSekAtBooking() {
        return totPriceSekAtBooking;
    }

    public void setTotPriceSekAtBooking(BigDecimal priceAtBooking) {
        this.totPriceSekAtBooking = priceAtBooking;
    }
}
