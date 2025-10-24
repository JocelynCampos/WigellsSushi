package se.edugrade.wigellssushi.dto;

import java.math.BigDecimal;

public class TotalCostDTO {

    BigDecimal roomCostSek;
    BigDecimal foodCostSek;
    String currency;
    BigDecimal totalCostFoodAndRoom;
    BigDecimal totalInCurrency;

    public TotalCostDTO(BigDecimal roomCostSek, BigDecimal foodCostSek, String currency, BigDecimal totalCostFoodAndRoom, BigDecimal totalInCurrency) {
        this.roomCostSek = roomCostSek;
        this.foodCostSek = foodCostSek;
        this.currency = currency;
        this.totalCostFoodAndRoom = totalCostFoodAndRoom;
        this.totalInCurrency = totalInCurrency;
    }

    public BigDecimal getRoomCostSek() {
        return roomCostSek;
    }

    public void setRoomCostSek(BigDecimal roomCostSek) {
        this.roomCostSek = roomCostSek;
    }

    public BigDecimal getFoodCostSek() {
        return foodCostSek;
    }

    public void setFoodCostSek(BigDecimal foodCostSek) {
        this.foodCostSek = foodCostSek;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getTotalCostFoodAndRoom() {
        return totalCostFoodAndRoom;
    }

    public void setTotalCostFoodAndRoom(BigDecimal totalCostFoodAndRoom) {
        this.totalCostFoodAndRoom = totalCostFoodAndRoom;
    }

    public BigDecimal getTotalInCurrency() {
        return totalInCurrency;
    }

    public void setTotalInCurrency(BigDecimal totalInCurrency) {
        this.totalInCurrency = totalInCurrency;
    }
}