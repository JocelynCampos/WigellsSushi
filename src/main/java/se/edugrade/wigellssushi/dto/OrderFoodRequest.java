package se.edugrade.wigellssushi.dto;

import java.util.Map;

public class OrderFoodRequest {
    private Integer bookingId;
    private String username;
    private Map<Integer, Integer> items;

    public Integer getBookingId() {
        return bookingId;
    }
    public void setBookingId(Integer bookingId) {
        this.bookingId = bookingId;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public Map<Integer, Integer> getItems() {
        return items;
    }
    public void setItems(Map<Integer, Integer> items) {
        this.items = items;
    }
}
