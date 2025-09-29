package se.edugrade.wigellssushi.exceptions;

public class BookingCancellationException extends RuntimeException {
    public BookingCancellationException(String message) {
        super(message);
    }
}
