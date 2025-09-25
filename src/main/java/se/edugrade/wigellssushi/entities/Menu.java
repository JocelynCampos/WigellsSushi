package se.edugrade.wigellssushi.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "menu")
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name_of_dish", nullable = false, length = 50)
    private String dishName;

    @Column (name = "price_per_plate", nullable = false)
    private double pricePerPlate;

}
