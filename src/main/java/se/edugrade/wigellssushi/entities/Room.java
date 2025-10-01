package se.edugrade.wigellssushi.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "room")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "room_name", nullable = false, unique = true, length = 100)
    private String roomName;

    @Column(name = "room_price", nullable = false, precision = 6, scale = 2)
    private BigDecimal roomPrice;

    public Room() {}

    public Room(String roomName, BigDecimal roomPrice) {
        this.roomName = roomName;
        this.roomPrice = roomPrice;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }
    public BigDecimal getRoomPrice() {
        return roomPrice;
    }
    public void setRoomPrice(BigDecimal roomPrice) {
        this.roomPrice = roomPrice;
    }
}
