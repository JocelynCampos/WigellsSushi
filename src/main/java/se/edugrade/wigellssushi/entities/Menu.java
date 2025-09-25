package se.edugrade.wigellssushi.entities;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "menu")
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name_of_dish", nullable = false, length = 100, unique = true)
    private String dishName;

    @Column (name = "price_per_plate", nullable = false, precision = 6, scale = 2)
    private BigDecimal pricePerPlate;

    public Menu() {}

    public Menu(String dishName, BigDecimal pricePerPlate) {
        this.dishName = dishName;
        this.pricePerPlate = pricePerPlate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public BigDecimal getPricePerPlate() {
        return pricePerPlate;
    }

    public void setPricePerPlate(BigDecimal pricePerPlate) {
        this.pricePerPlate = pricePerPlate;
    }
}
