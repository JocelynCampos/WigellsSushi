package se.edugrade.wigellssushi.dto;

import se.edugrade.wigellssushi.entities.BookingRoom;

import java.time.LocalDate;

public record RoomBookingDTO(
        Integer id,
        Integer roomId,
        Integer userId,
        LocalDate startDate,
        LocalDate endDate,
        Integer guests,
        String status
) {


    public static RoomBookingDTO of(BookingRoom br) {
        return new RoomBookingDTO(
            br.getId(),
            br.getRoom() != null ? br.getRoom().getId() : null,
            br.getUser() != null ? br.getUser().getId() : null,
            br.getStartDate(),
            br.getEndDate(),
            br.getGuestCount(),
            br.getStatus() != null ? br.getStatus().name() : null
        );
    }
}


